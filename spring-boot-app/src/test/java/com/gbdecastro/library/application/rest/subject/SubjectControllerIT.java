package com.gbdecastro.library.application.rest.subject;

import com.gbdecastro.library.application.rest.controller.subject.SubjectController;
import com.gbdecastro.library.application.rest.controller.subject.SubjectRequest;
import com.gbdecastro.library.application.rest.shared.BaseIT;
import com.gbdecastro.library.application.rest.shared.annotations.ParameterizedStringTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION_OTHER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class SubjectControllerIT extends BaseIT {

    private final String BASE_URI = SubjectController.API_URL;
    private final String BASE_URI_ID = SubjectController.API_URL.concat("/{id}");

    // region data
    private SubjectRequest subjectRequestIT;
    // endregion

    @AfterAll
    void cleanUp() {
        cleanDB();
    }

    // region Tests
    @Test
    @DisplayName("Getting all subjects: Should Return all subjects")
    void findAll_shouldReturnAll() throws Exception {
        givenSubject();
        whenRequestFindAll();
        thenShouldReturnAll();
    }

    @Test
    @DisplayName("Finding one subject by id: Should return a subject")
    void findOne_shouldReturnOneSubject() throws Exception {
        givenSubject();
        whenRequestFindOne(subjectIT.getId());
        thenShouldReturnASubject();
    }

    @Test
    @DisplayName("Finding one subject by id: Should return Domain Exception: 'Subject Not Found'")
    void findOne_shouldReturnEntityNotFound() throws Exception {
        whenRequestFindOne(INVALID_ID);
        thenShouldReturnEntityNotFound(messageContext.getMessage("subject_not_found"));
    }

    @Test
    @DisplayName("Creating a subject: Should return the saved subject")
    void create_shouldReturnTheCreatedSubject() throws Exception {
        givenSubjectRequest(SUBJECT_DESCRIPTION_OTHER);
        whenRequestCreate(subjectRequestIT);
        thenShouldReturnACreatedSubject();
    }

    @ParameterizedStringTest
    @DisplayName("Creating a subject: Should return Domain Exception: 'subject description is requried'")
    void create_shouldReturnDomainException_subjectNameIsRequired(String name) throws Exception {
        givenSubjectRequest(name);
        whenRequestCreate(subjectRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("description_required"));
    }

    @Test
    @DisplayName("Updating a subject: Should return the updated subject")
    void update_shouldReturnTheUpdatedSubject() throws Exception {
        givenSubject();
        givenSubjectRequest(SUBJECT_DESCRIPTION_OTHER);
        whenRequestUpdate(subjectIT.getId(), subjectRequestIT);
        thenShouldReturnUpdatedSubject();
    }

    @Test
    @DisplayName("Updating a subject: Should return Domain Exception: 'Subject Not Found'")
    void update_shouldReturnDomainException_subjectNotFound() throws Exception {
        givenSubjectRequest(SUBJECT_DESCRIPTION_OTHER);
        whenRequestUpdate(INVALID_ID, subjectRequestIT);
        thenShouldReturnEntityNotFound(messageContext.getMessage("subject_not_found"));
    }

    @ParameterizedStringTest
    @DisplayName("Updating a subject: Should return Domain Exception: 'Subject description is required'")
    void update_shouldReturnDomainException_subjectNameIsInvalid(String name) throws Exception {
        givenSubject();
        givenSubjectRequest(name);
        whenRequestUpdate(subjectIT.getId(), subjectRequestIT);
        thenShouldReturnDomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("description_required"));
    }

    @Test
    @DisplayName("Delete a subject by id: Should Return 204 code: No Content")
    void delete_shouldReturnNoContent() throws Exception {
        givenSubject();
        whenRequestDelete(subjectIT.getId());
        thenShouldReturnNoContent();
    }

    @Test
    @DisplayName("Delete a subject by id: Should return Domain Exception: 'Subject not found'")
    void delete_shouldReturnDomainException_SubjectNotFound() throws Exception {
        whenRequestDelete(INVALID_ID);
        thenShouldReturnDomainException(HttpStatus.NOT_FOUND.value(), messageContext.getMessage("subject_not_found"));
    }

    // endregion

    // region given
    protected void givenSubjectRequest(String description) {
        subjectRequestIT = SubjectRequest.builder()
                .description(description)
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

    private void whenRequestCreate(SubjectRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder =
                post(BASE_URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestUpdate(Long id, SubjectRequest request) throws Exception {

        MockHttpServletRequestBuilder requestBuilder =
                put(BASE_URI_ID, id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(request));


        perform = mockMvc.perform(requestBuilder);
    }

    private void whenRequestDelete(Long subjectId) throws Exception {
        perform = mockMvc.perform(delete(BASE_URI_ID, subjectId));
    }
    // endregion

    // region then
    private void thenShouldReturnAll() throws Exception {
        perform.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.subjectResponseList").isArray())
                .andExpect(jsonPath("$._embedded.subjectResponseList[0].id").value(subjectIT.getId()))
                .andExpect(jsonPath("$._embedded.subjectResponseList[0].description").value(subjectIT.getDescription()));
    }

    private void thenShouldReturnASubject() throws Exception {
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subjectIT.getId()))
                .andExpect(jsonPath("$.description").value(subjectIT.getDescription()));
    }

    private void thenShouldReturnACreatedSubject() throws Exception {
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value(subjectRequestIT.getDescription()));
    }

    private void thenShouldReturnUpdatedSubject() throws Exception{
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value(subjectRequestIT.getDescription()));
    }

    // endregion
}