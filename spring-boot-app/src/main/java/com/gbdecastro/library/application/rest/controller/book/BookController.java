package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.application.rest.annotation.BaseController;
import com.gbdecastro.library.domain.book.Book;
import com.gbdecastro.library.domain.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<Book> authors = service.findAll();

        List<BookResponse> response = mapper.toResponseList(authors);

        Link self = linkTo(methodOn(getClass()).findAll()).withSelfRel();
        Link create = BookLinks.create(null);
        Link update = BookLinks.update(null, null);
        Link delete = BookLinks.delete(null);
        Link findOne = BookLinks.findOne(null);

        CollectionModel<BookResponse> entityModel = CollectionModel.of(response, self, create, update, delete, findOne);

        return ResponseEntity.ok().body(entityModel);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookResponse>> findOne(Long id) {
        BookResponse response = mapper.toResponse(service.findById(id));

        Link self = linkTo(methodOn(getClass()).findOne(id)).withSelfRel();
        Link update = BookLinks.update(id, null);
        Link delete = BookLinks.delete(id);

        EntityModel<BookResponse> entityModel = EntityModel.of(response, self, update, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<BookResponse>> create(BookRequest bookRequest) {
        BookResponse response = this.mapper.toResponse(service.create(bookRequest));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        Link selfLink = linkTo(methodOn(getClass()).create(bookRequest)).withSelfRel();
        Link findOne = BookLinks.findOne(response.getId());
        Link update = BookLinks.update(response.getId(), null);
        Link delete = BookLinks.delete(response.getId());

        EntityModel<BookResponse> entityModel = EntityModel.of(response, selfLink, findOne, update, delete);

        return ResponseEntity.created(location).body(entityModel);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookResponse>> update(Long id, BookRequest bookRequest) {
        BookResponse response = mapper.toResponse(service.update(id, bookRequest));

        Link selfLink = linkTo(methodOn(getClass()).update(id, bookRequest)).withSelfRel();
        Link findOne = BookLinks.findOne(id);
        Link delete = BookLinks.delete(id);

        EntityModel<BookResponse> entityModel = EntityModel.of(response, selfLink, findOne, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
