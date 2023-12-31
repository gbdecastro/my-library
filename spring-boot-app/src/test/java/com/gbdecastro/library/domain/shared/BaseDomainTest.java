package com.gbdecastro.library.domain.shared;

import com.gbdecastro.library.application.rest.controller.author.AuthorResponse;
import com.gbdecastro.library.application.rest.controller.book.BookRequest;
import com.gbdecastro.library.application.rest.controller.subject.SubjectResponse;
import com.gbdecastro.library.domain.author.Author;
import com.gbdecastro.library.domain.book.Book;
import com.gbdecastro.library.domain.shared.message.MessageContext;
import com.gbdecastro.library.domain.subject.Subject;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.yml")
public class BaseDomainTest {
    // region Data
    protected final String AUTHOR_NOT_FOUND = "author_not_found";
    protected final String BOOK_NOT_FOUND = "book_not_found";
    protected final String SUBJECT_NOT_FOUND = "subject_not_found";
    protected final Long INVALID_ID = -1L;
    protected Book bookTest;
    protected BookRequest bookRequestTest;
    protected Author authorTest;
    protected Author authorTest2;
    protected Subject subjectTest;
    protected Subject subjectTest2;

    @Mock
    protected MessageContext messageContext;

    // endregion

    // region given(s)
    protected void givenAuthor(Long id, String name) {
        authorTest = Author.builder().id(id).name(name).build();
    }

    protected void givenAuthor2(Long id, String name) {
        authorTest2 = Author.builder().id(id).name(name).build();
    }

    protected void givenSubject(Long id, String description) {
        subjectTest = Subject.builder().id(id).description(description).build();
    }

    protected void givenSubject2(Long id, String description) {
        subjectTest2 = Subject.builder().id(id).description(description).build();
    }

    protected void givenBook(Long id, String title, Integer edition, String publicationYear, Double price) {
        bookTest = Book.builder().id(id).title(title).edition(edition).price(price).publicationYear(publicationYear).build();
    }

    protected void givenBookRequest(String title, Integer edition, Double price, String publicationYear, Set<Author> authors, Set<Subject> subjects) {

        Set<SubjectResponse> subjectResponses = new HashSet<>();
        Set<AuthorResponse> authorResponses = new HashSet<>();

        if (!Objects.isNull(authors)) {
            authorResponses =
                authors.stream().map((item) -> AuthorResponse.builder().id(item.getId()).name(item.getName()).build()).collect(Collectors.toSet());
        }

        if (!Objects.isNull(subjects)) {
            subjectResponses = subjects.stream().map((item) -> SubjectResponse.builder().id(item.getId()).description(item.getDescription()).build())
                .collect(Collectors.toSet());
        }

        bookRequestTest = BookRequest.builder().title(title).price(price).edition(edition).publicationYear(publicationYear).authors(authorResponses)
            .subjects(subjectResponses).build();
    }

    protected void givenAnAuthorToBook(Book book, Author author) {
        author.addBook(book);
    }

    protected void givenASubjectToBook(Book book, Subject subject) {
        subject.addBook(book);
    }
    // endregion

    // region when(s)
    protected void whenCalling_messageContext(String message) {
        when(messageContext.getMessage(message)).thenReturn(message);
    }
    // endregion

    // region then (s)
    protected void thenThrowDomainException(Executable executable, int code, String message) {
        DomainException exception = assertThrows(DomainException.class, executable);
        assertThat(exception.getMessage()).contains(message);
        assertThat(exception.getCode()).isEqualTo(code);
    }

    protected void thenThrowEntityNotFoundException(Executable executable, String message) {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, executable);
        assertThat(exception.getMessage()).isEqualTo(message);
    }
    // endregion
}
