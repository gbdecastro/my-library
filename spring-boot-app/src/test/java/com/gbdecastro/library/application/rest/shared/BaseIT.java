package com.gbdecastro.library.application.rest.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbdecastro.library.application.rest.controller.author.AuthorRequest;
import com.gbdecastro.library.application.rest.controller.book.BookRequest;
import com.gbdecastro.library.application.rest.controller.subject.SubjectRequest;
import com.gbdecastro.library.domain.author.Author;
import com.gbdecastro.library.domain.author.AuthorRepository;
import com.gbdecastro.library.domain.book.Book;
import com.gbdecastro.library.domain.book.BookRepository;
import com.gbdecastro.library.domain.shared.message.MessageContext;
import com.gbdecastro.library.domain.subject.Subject;
import com.gbdecastro.library.domain.subject.SubjectRepository;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PRICE;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BaseIT {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected ResultActions perform;

    // region Repositories
    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected MessageContext messageContext;
    // endregion

    // region Data
    protected Long INVALID_ID = -1L;
    protected Book bookIT;
    protected BookRequest bookRequestIT;

    protected Author authorIT;
    protected AuthorRequest authorRequestIT;

    protected Subject subjectIT;
    protected SubjectRequest subjectRequestIT;
    // endregion


    protected void givenAuthor() {
        authorIT = Author.builder().name(AUTHOR_NAME).build();
        authorIT = authorRepository.save(authorIT);
    }

    protected void givenBook() {
        bookIT = Book.builder().title(BOOK_TITLE).edition(BOOK_EDITION).publicationYear(BOOK_PUB_YEAR).price(BOOK_PRICE).build();

        bookIT = bookRepository.save(bookIT);
    }

    protected void givenSubject() {
        subjectIT = Subject.builder().description(SUBJECT_DESCRIPTION).build();
        subjectIT = subjectRepository.save(subjectIT);
    }

    // region then
    protected void thenShouldReturnDomainException(int code, String msg) throws Exception {
        perform.andExpect(status().is(code)).andExpect(jsonPath("$.message").value(msg));
    }

    protected void thenShouldReturnEntityNotFound(String msg) throws Exception {
        perform.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andExpect(jsonPath("$.message").value(msg));
    }

    protected void thenShouldReturnNoContent() throws Exception {
        perform.andExpect(status().isNoContent());
    }
    // endregion

    protected void cleanDB() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        subjectRepository.deleteAll();
    }
}
