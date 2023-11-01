package com.gbdecastro.library.domain.book;

import com.gbdecastro.library.application.rest.controller.book.BookMapper;
import com.gbdecastro.library.application.rest.controller.book.BookRequest;
import com.gbdecastro.library.domain.author.AuthorService;
import com.gbdecastro.library.domain.shared.BaseDomainTest;
import com.gbdecastro.library.domain.subject.SubjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_ID;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE_LARGE;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE_OTHER;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_ID;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest extends BaseDomainTest {

    // region data
    @InjectMocks
    private BookServiceImpl service;

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper mapper;

    @Mock
    private AuthorService authorService;

    @Mock
    private SubjectService subjectService;

    private List<Book> authorListTest;
    private List<Book> bookListResult;
    private Book bookResult;

    // endregion

    // region Tests
    @Test
    @DisplayName("List all books")
    void findAll_shouldReturnABookList() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        whenCalling_repositoryFindAll();
        whenCalled_findAll();
        thenShouldReturnABookList();
    }

    @Test
    @DisplayName("List a book by id")
    void findById_shouldReturnABook() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        whenCalling_repositoryFindById(bookTest.getId(), Optional.of(bookTest));
        whenCalled_findById(bookTest.getId());
        thenShouldReturnABook(bookTest.getId());
    }

    @Test
    @DisplayName("Finding a book by id but an exception is expected: book not found")
    void findById_throwEntityNotFoundException() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        whenCalling_repositoryFindById(bookTest.getId(), Optional.empty());
        whenCalling_messageContext(BOOK_NOT_FOUND);
        thenThrowEntityNotFoundException(() -> service.findById(BOOK_ID), BOOK_NOT_FOUND);
    }

    @Test
    @DisplayName("Creating a book with valid data should succeed")
    void create_shouldSucceedWithValidData() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenBookRequest(BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR, Set.of(authorTest.getId()), Set.of(subjectTest.getId()));
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);

        whenCalling_repositorySave(bookTest);
        whenCalled_create(bookRequestTest);

        assertEquals(bookTest, bookResult);
    }

    @Test
    @DisplayName("Creating a book with missing title should throw DomainException")
    void create_throwDomainExceptionWhenTitleMissing() {
        givenBookRequest(null, BOOK_EDITION, BOOK_PUB_YEAR, null, null);
        whenCalling_messageContext("title_required");
        thenThrowDomainException(() -> service.create(bookRequestTest), HttpStatus.BAD_REQUEST.value(), "title_required");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Creating a book with title exceeding 40 characters should throw DomainException")
    void create_throwDomainExceptionWhenTitleTooLong() {
        givenBookRequest(BOOK_TITLE_LARGE, BOOK_EDITION, BOOK_PUB_YEAR, null, null);
        whenCalling_messageContext("title_max_length");

        thenThrowDomainException(() -> service.create(bookRequestTest), HttpStatus.BAD_REQUEST.value(), "title_max_length");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Updating a book with valid data should succeed")
    void update_shouldSucceedWithValidData() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenBookRequest(BOOK_TITLE_OTHER, BOOK_EDITION, BOOK_PUB_YEAR, Set.of(authorTest.getId()), Set.of(subjectTest.getId()));
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);

        whenCalling_repositoryFindById(BOOK_ID, Optional.of(bookTest));
        whenCalling_repositorySaveAndFlush(bookTest);

        whenCalled_update(BOOK_ID, bookRequestTest);


        assertNotNull(bookResult);
        assertEquals(BOOK_TITLE_OTHER, bookResult.getTitle());
    }

    @Test
    @DisplayName("Deleting a book should succeed")
    void delete_shouldSucceed() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        whenCalling_repositoryFindById(BOOK_ID, Optional.of(bookTest));

        whenCalled_delete(BOOK_ID);

        verify(repository, times(1)).delete(bookTest);
    }

    // endregion

    // region when calling(s)
    private void whenCalling_repositoryFindAll() {
        when(repository.findAll()).thenReturn(List.of(bookTest));
    }

    private void whenCalling_repositoryFindById(Long id, Optional<Book> book) {
        when(repository.findById(id)).thenReturn(book);
    }

    private void whenCalling_repositorySave(Book book) {
        when(repository.save(any())).thenReturn(book);
    }

    private void whenCalling_repositorySaveAndFlush(Book book) {
        when(repository.saveAndFlush(book)).thenReturn(book);
    }

    private void whenCalled_findAll() {
        bookListResult = service.findAll();
    }

    private void whenCalled_findById(Long id) {
        bookResult = service.findById(id);
    }

    private void whenCalled_create(BookRequest bookRequest) {
        bookResult = service.create(bookRequest);
    }

    private void whenCalled_update(Long id, BookRequest bookRequest) {
        bookResult = service.update(id, bookRequest);
    }

    private void whenCalled_delete(Long id) {
        service.delete(id);
    }

    // endregion

    // region then(s)

    private void thenShouldReturnABookList() {
        assertAll(() -> verify(repository, times(1)).findAll(), () -> assertEquals(bookTest, bookListResult.get(0)));
    }

    private void thenShouldReturnABook(Long id) {
        assertAll(() -> verify(repository, times(1)).findById(id), () -> assertEquals(bookTest, bookResult));
    }

    // endregion
}
