package com.gbdecastro.library.application.rest.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookApi {

    @Operation(summary = "List all Books")
    @ApiResponse(responseCode = "200", description = "Found Books")
    ResponseEntity<CollectionModel<BookResponse>> findAll();

    @Operation(summary = "Find a Book by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book found"), @ApiResponse(responseCode = "404", description = "Book not found"),})
    ResponseEntity<EntityModel<BookResponse>> findOne(@PathVariable @Parameter(description = "An id of Book") Long id);

    @Operation(summary = "Create a Book")
    @ApiResponses(
        value = {@ApiResponse(responseCode = "200", description = "Book created"), @ApiResponse(responseCode = "400", description = "Somethings was wrong!")})
    ResponseEntity<EntityModel<BookResponse>> create(@RequestBody BookRequest bookRequest);

    @Operation(summary = "Update a Book")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book updated"),
        @ApiResponse(responseCode = "400", description = "Somethings was wrong!"), @ApiResponse(responseCode = "404", description = "Book not found")})
    ResponseEntity<EntityModel<BookResponse>> update(@PathVariable @Parameter(description = "An id of Book") Long id, @RequestBody BookRequest bookRequest);

    @Operation(summary = "Delete a Book by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Book found"), @ApiResponse(responseCode = "404", description = "Book not found"),})
    ResponseEntity<Void> delete(@PathVariable @Parameter(description = "An id of Book") Long id);

}
