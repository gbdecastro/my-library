package com.gbdecastro.library.application.rest.controller.subject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface SubjectApi {

    @Operation(summary = "List all Subjects")
    @ApiResponse(responseCode = "200", description = "Found Subjects")
    ResponseEntity<CollectionModel<SubjectResponse>> findAll();

    @Operation(summary = "Find a Subject by id")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Subject found"), @ApiResponse(responseCode = "404", description = "Subject not found"),})
    ResponseEntity<EntityModel<SubjectResponse>> findOne(@PathVariable @Parameter(description = "An id of Subject") Long id);

    @Operation(summary = "Create a Subject")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Subject created"),
        @ApiResponse(responseCode = "400", description = "Somethings was wrong!")})
    ResponseEntity<EntityModel<SubjectResponse>> create(@RequestBody SubjectRequest subjectRequest);

    @Operation(summary = "Update a Subject")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Subject updated"),
        @ApiResponse(responseCode = "400", description = "Somethings was wrong!"), @ApiResponse(responseCode = "404", description = "Subject not found")})
    ResponseEntity<EntityModel<SubjectResponse>> update(@PathVariable @Parameter(description = "An id of Subject") Long id,
        @RequestBody SubjectRequest subjectRequest);

    @Operation(summary = "Delete a Subject by id")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Subject found"), @ApiResponse(responseCode = "404", description = "Subject not found"),})
    ResponseEntity<Void> delete(@PathVariable @Parameter(description = "An id of Subject") Long id);

}
