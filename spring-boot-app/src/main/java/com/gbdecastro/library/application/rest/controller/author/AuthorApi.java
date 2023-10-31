package com.gbdecastro.library.application.rest.controller.author;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthorApi {

    @Operation(summary = "List all Authors")
    @ApiResponse(responseCode = "200", description = "Found Authors")
    ResponseEntity<CollectionModel<AuthorResponse>> findAll();

    @Operation(summary = "Find a Author by id")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Author found"), @ApiResponse(responseCode = "404", description = "Author not found"),})
    ResponseEntity<EntityModel<AuthorResponse>> findOne(@PathVariable @Parameter(description = "An id of Author") Long id);

    @Operation(summary = "Create a Author")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Author created"), @ApiResponse(responseCode = "400", description = "Somethings was wrong!")})
    ResponseEntity<EntityModel<AuthorResponse>> create(@RequestBody AuthorRequest authorRequest);

    @Operation(summary = "Update a Author")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Author updated"),
        @ApiResponse(responseCode = "400", description = "Somethings was wrong!"), @ApiResponse(responseCode = "404", description = "Author not found")})
    ResponseEntity<EntityModel<AuthorResponse>> update(@PathVariable @Parameter(description = "An id of Author") Long id,
        @RequestBody AuthorRequest authorRequest);

    @Operation(summary = "Delete a Author by id")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Author found"), @ApiResponse(responseCode = "404", description = "Author not found"),})
    ResponseEntity<Void> delete(@PathVariable @Parameter(description = "An id of Author") Long id);

}
