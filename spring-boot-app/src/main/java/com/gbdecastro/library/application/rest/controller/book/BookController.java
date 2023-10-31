package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.application.rest.annotation.BaseController;
import com.gbdecastro.library.domain.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@BaseController(BookController.API_URL)

public class BookController implements BookApi {
    public static final String API_URL = "/api/v1/books";

    @Autowired
    private BookService service;

    @Autowired
    private BookMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<BookResponse>> findAll() {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookResponse>> findOne(Long id) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<BookResponse>> create(BookRequest bookRequest) {
        return null;
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<BookResponse>> update(Long id, BookRequest bookRequest) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}
