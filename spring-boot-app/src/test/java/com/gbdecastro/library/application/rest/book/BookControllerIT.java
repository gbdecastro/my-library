package com.gbdecastro.library.application.rest.book;

import com.gbdecastro.library.application.rest.controller.book.BookController;
import com.gbdecastro.library.application.rest.controller.book.BookRequest;
import com.gbdecastro.library.application.rest.shared.BaseIT;
import com.gbdecastro.library.application.rest.shared.annotations.ParameterizedStringTest;
import com.gbdecastro.library.domain.author.Author;
import com.gbdecastro.library.domain.subject.Subject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.util.Set;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE_OTHER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class BookControllerIT extends BaseIT {

    private final String BASE_URI = BookController.API_URL;
    private final String BASE_URI_ID = BookController.API_URL.concat("/{id}");

    // region data
    private BookRequest bookRequestIT;
    // endregion

    @AfterAll
    void cleanUp() {
        cleanDB();
    }

    // region Tests
    @Test
    @DisplayName("Getting all books: Should Return all books")
    void findAll_shouldReturnAll() throws Exception {
        givenBook();
        whenRequestFindAll();
        thenShouldReturnAll();
    }

    @Test
    @DisplayName("Finding one book by id: Should return a book")
    void findOne_shouldReturnOneBook() throws Exception {
        givenBook();
        whenRequestFindOne(bookIT.getId());
        thenShouldReturnABook();
    }

    @Test
    @DisplayName("Finding one book by id: Should return Domain Exception: 'Book Not Found'")
    void findOne_shouldReturnEntityNotFound() throws Exception {
        whenRequestFindOne(INVALID_ID);
        thenShouldReturnEntityNotFound(messageContext.getMessage("book_not_found"));
    }

    @Test
    @DisplayName("Creating a book: Should return the saved book")
    void create_shouldReturnTheCreatedBook() throws Exception {
        givenAuthor();
        givenSubject();
        givenBookRequest(BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR, authorIT, subjectIT);
        whenRequestCreate(bookRequestIT);
        thenShouldReturnACreatedBook();
    }

    @ParameterizedStringTest
    @DisplayName("Creating a book: Should return Domain Exception: 'book title is requried'")
    void create_shouldReturnDomainException_bookTitleIsRequired(String title) throws Exception {
        givenAuthor();
        givenSubject();
        givenBookRequest(title, BOOK_EDITION, BOOK_PUB_YEAR, authorIT, subjectIT);
        whenRequestCreate(bookRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("title_required"));
    }

    @Test
    @DisplayName("Updating a book: Should return the updated book")
    void update_shouldReturnTheUpdatedBook() throws Exception {
        givenBook();
        givenAuthor();
        givenSubject();
        givenBookRequest(BOOK_TITLE_OTHER, BOOK_EDITION, BOOK_PUB_YEAR, authorIT, subjectIT);
        whenRequestUpdate(bookIT.getId(), bookRequestIT);
        thenShouldReturnUpdatedBook();
    }

    @Test
    @DisplayName("Updating a book: Should return Domain Exception: 'Book Not Found'")
    void update_shouldReturnDomainException_bookNotFound() throws Exception {
        whenRequestUpdate(INVALID_ID, bookRequestIT);
        thenShouldReturnEntityNotFound(messageContext.getMessage("book_not_found"));
    }

    @ParameterizedStringTest
    @DisplayName("Updating a book: Should return Domain Exception: 'Book title is required'")
    void update_shouldReturnDomainException_bookNameIsInvalid(String title) throws Exception {
        givenBook();
        givenAuthor();
        givenSubject();
        givenBookRequest(title, BOOK_EDITION, BOOK_PUB_YEAR, authorIT, subjectIT);
        whenRequestUpdate(bookIT.getId(), bookRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("title_required"));
    }

    @Test
    @DisplayName("Delete a book by id: Should Return 204 code: No Content")
    void delete_shouldReturnNoContent() throws Exception {
        givenBook();
        whenRequestDelete(bookIT.getId());
        thenShouldReturnNoContent();
    }

    @Test
    @DisplayName("Delete a book by id: Should return Domain Exception: 'Book not found'")
    void delete_shouldReturnDomainException_BookNotFound() throws Exception {
        whenRequestDelete(INVALID_ID);
        thenShouldReturnDomainException(HttpStatus.NOT_FOUND.value(), messageContext.getMessage("book_not_found"));
    }

    // endregion

    // region given
    protected void givenBookRequest(String title, Integer edition, String publicationYear, Author author, Subject subject) {
        bookRequestIT = BookRequest.builder().title(title).edition(edition).publicationYear(publicationYear).subjects(Set.of(subject.getId()))
            .authors(Set.of(author.getId())).build();
    }
    // endregion

    // region whenRequest
    private void whenRequestFindAll() throws Exception {
        perform = mockMvc.perform(get(BASE_URI));
    }

    private void whenRequestFindOne(Long id) throws Exception {
        perform = mockMvc.perform(get(BASE_URI_ID, id));
    }

    private void whenRequestCreate(BookRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post(BASE_URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestUpdate(Long id, BookRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder =
            put(BASE_URI_ID, id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestDelete(Long bookId) throws Exception {
        perform = mockMvc.perform(delete(BASE_URI_ID, bookId));
    }
    // endregion

    // region then
    private void thenShouldReturnAll() throws Exception {
        perform.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.bookResponseList").isArray())
            .andExpect(jsonPath("$._embedded.bookResponseList[0].id").value(bookIT.getId()))
            .andExpect(jsonPath("$._embedded.bookResponseList[0].title").value(bookIT.getTitle()))
            .andExpect(jsonPath("$._embedded.bookResponseList[0].publicationYear").value(bookIT.getPublicationYear()))
            .andExpect(jsonPath("$._embedded.bookResponseList[0].edition").value(bookIT.getEdition()));
    }

    private void thenShouldReturnABook() throws Exception {
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(bookIT.getId())).andExpect(jsonPath("$.title").value(bookIT.getTitle()))
            .andExpect(jsonPath("$.edition").value(bookIT.getEdition())).andExpect(jsonPath("$.publicationYear").value(bookIT.getPublicationYear()));
    }

    private void thenShouldReturnACreatedBook() throws Exception {
        perform.andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNotEmpty()).andExpect(jsonPath("$.title").value(bookRequestIT.getTitle()))
            .andExpect(jsonPath("$.edition").value(bookRequestIT.getEdition()))
            .andExpect(jsonPath("$.publicationYear").value(bookRequestIT.getPublicationYear()));
    }

    private void thenShouldReturnUpdatedBook() throws Exception {
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.id").isNotEmpty()).andExpect(jsonPath("$.title").value(bookIT.getTitle()))
            .andExpect(jsonPath("$.edition").value(bookIT.getEdition())).andExpect(jsonPath("$.publicationYear").value(bookIT.getPublicationYear()));
    }

    // endregion
}
