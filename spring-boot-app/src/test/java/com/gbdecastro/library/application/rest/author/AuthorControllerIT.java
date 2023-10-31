package com.gbdecastro.library.application.rest.author;

import com.gbdecastro.library.application.rest.controller.author.AuthorController;
import com.gbdecastro.library.application.rest.controller.author.AuthorRequest;
import com.gbdecastro.library.application.rest.shared.BaseIT;
import com.gbdecastro.library.application.rest.shared.annotations.ParameterizedStringTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME_OTHER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class AuthorControllerIT extends BaseIT {

    private final String BASE_URI = AuthorController.API_URL;
    private final String BASE_URI_ID = AuthorController.API_URL.concat("/{id}");

    // region data
    private AuthorRequest authorRequestIT;
    // endregion

    @AfterAll
    void cleanUp() {
        cleanDB();
    }

    // region Tests
    @Test
    @DisplayName("Getting all authors: Should Return all authors")
    void findAll_shouldReturnAll() throws Exception {
        givenAuthor();
        whenRequestFindAll();
        thenShouldReturnAll();
    }

    @Test
    @DisplayName("Finding one author by id: Should return a author")
    void findOne_shouldReturnOneAuthor() throws Exception {
        givenAuthor();
        whenRequestFindOne(authorIT.getId());
        thenShouldReturnAAuthor();
    }

    @Test
    @DisplayName("Finding one author by id: Should return Domain Exception: 'Author Not Found'")
    void findOne_shouldReturnEntityNotFound() throws Exception {
        whenRequestFindOne(INVALID_ID);
        thenShouldReturnEntityNotFound(messageContext.getMessage("author_not_found"));
    }

    @Test
    @DisplayName("Creating a author: Should return the saved author")
    void create_shouldReturnTheCreatedAuthor() throws Exception {
        givenAuthorRequest(AUTHOR_NAME);
        whenRequestCreate(authorRequestIT);
        thenShouldReturnACreatedAuthor();
    }

    @ParameterizedStringTest
    @DisplayName("Creating a author: Should return Domain Exception: 'author name is requried'")
    void create_shouldReturnDomainException_authorNameIsRequired(String name) throws Exception {
        givenAuthorRequest(name);
        whenRequestCreate(authorRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("name_required"));
    }

    @Test
    @DisplayName("Updating a author: Should return the updated author")
    void update_shouldReturnTheUpdatedAuthor() throws Exception {
        givenAuthor();
        givenAuthorRequest(AUTHOR_NAME_OTHER);
        whenRequestUpdate(authorIT.getId(), authorRequestIT);
        thenShouldReturnUpdatedAuthor();
    }

    @Test
    @DisplayName("Updating a author: Should return Domain Exception: 'Author Not Found'")
    void update_shouldReturnDomainException_authorNotFound() throws Exception {
        givenAuthorRequest(AUTHOR_NAME_OTHER);
        whenRequestUpdate(INVALID_ID, authorRequestIT);
        thenShouldReturnEntityNotFound(messageContext.getMessage("author_not_found"));
    }

    @ParameterizedStringTest
    @DisplayName("Updating a author: Should return Domain Exception: 'Author name is required'")
    void update_shouldReturnDomainException_authorNameIsInvalid(String name) throws Exception {
        givenAuthor();
        givenAuthorRequest(name);
        whenRequestUpdate(authorIT.getId(), authorRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("name_required"));
    }

    @Test
    @DisplayName("Delete a author by id: Should Return 204 code: No Content")
    void delete_shouldReturnNoContent() throws Exception {
        givenAuthor();
        whenRequestDelete(authorIT.getId());
        thenShouldReturnNoContent();
    }

    @Test
    @DisplayName("Delete a author by id: Should return Domain Exception: 'Author not found'")
    void delete_shouldReturnDomainException_AuthorNotFound() throws Exception {
        whenRequestDelete(INVALID_ID);
        thenShouldReturnDomainException(HttpStatus.NOT_FOUND.value(), messageContext.getMessage("author_not_found"));
    }

    // endregion

    // region given
    protected void givenAuthorRequest(String name) {
        authorRequestIT = AuthorRequest.builder()
                .name(name)
                .build();
    }
    // endregion

    // region whenRequest
    private void whenRequestFindAll() throws Exception {
        perform = mockMvc.perform(get(BASE_URI));
    }

    private void whenRequestFindOne(Long id) throws Exception {
        perform = mockMvc.perform(get(BASE_URI_ID, id));
    }

    private void whenRequestCreate(AuthorRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestUpdate(Long id, AuthorRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder =
                put(BASE_URI_ID, id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestDelete(Long authorId) throws Exception {
        perform = mockMvc.perform(delete(BASE_URI_ID, authorId));
    }
    // endregion

    // region then
    private void thenShouldReturnAll() throws Exception {
        perform.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.authorResponseList").isArray())
                .andExpect(jsonPath("$._embedded.authorResponseList[0].id").value(authorIT.getId()))
                .andExpect(jsonPath("$._embedded.authorResponseList[0].name").value(authorIT.getName()));
    }

    private void thenShouldReturnAAuthor() throws Exception {
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorIT.getId()))
                .andExpect(jsonPath("$.name").value(authorIT.getName()));
    }

    private void thenShouldReturnACreatedAuthor() throws Exception {
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(authorRequestIT.getName()));
    }

    private void thenShouldReturnUpdatedAuthor() throws Exception{
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(authorRequestIT.getName()));
    }

    // endregion
}