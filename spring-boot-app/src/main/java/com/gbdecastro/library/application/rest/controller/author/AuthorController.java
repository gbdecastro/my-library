package com.gbdecastro.library.application.rest.controller.author;

import com.gbdecastro.library.application.rest.annotation.BaseController;
import com.gbdecastro.library.domain.author.Author;
import com.gbdecastro.library.domain.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@BaseController(AuthorController.API_URL)
public class AuthorController implements AuthorApi {
    public static final String API_URL = "/api/v1/authors";

    @Autowired
    private AuthorService service;
    @Autowired
    private AuthorMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<AuthorResponse>> findAll() {
        List<Author> authors = service.findAll();

        List<AuthorResponse> response = mapper.toResponseList(authors);

        Link self = linkTo(methodOn(getClass()).findAll()).withSelfRel();
        Link create = AuthorLinks.create(null);
        Link update = AuthorLinks.update(null, null);
        Link delete = AuthorLinks.delete(null);
        Link findOne = AuthorLinks.findOne(null);

        CollectionModel<AuthorResponse> entityModel = CollectionModel.of(response, self, create, update, delete, findOne);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AuthorResponse>> findOne(Long id) {
        AuthorResponse response = mapper.toResponse(service.findById(id));

        Link self = linkTo(methodOn(getClass()).findOne(id)).withSelfRel();
        Link update = AuthorLinks.update(id, null);
        Link delete = AuthorLinks.delete(id);

        EntityModel<AuthorResponse> entityModel = EntityModel.of(response, self, update, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<AuthorResponse>> create(@Valid @RequestBody AuthorRequest authorRequest) {
        Author author = this.mapper.requestToAuthor(authorRequest);
        AuthorResponse response = this.mapper.toResponse(this.service.create(author));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        Link selfLink = linkTo(methodOn(getClass()).create(authorRequest)).withSelfRel();
        Link findOne = AuthorLinks.findOne(response.getId());
        Link update = AuthorLinks.update(response.getId(), null);
        Link delete = AuthorLinks.delete(response.getId());


        EntityModel<AuthorResponse> entityModel = EntityModel.of(response, selfLink, findOne, update, delete);

        return ResponseEntity.created(location).body(entityModel);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AuthorResponse>> update(Long id, @Valid @RequestBody AuthorRequest authorRequest) {
        AuthorResponse response = mapper.toResponse(service.update(id, mapper.requestToAuthor(authorRequest)));

        Link selfLink = linkTo(methodOn(getClass()).update(id, authorRequest)).withSelfRel();
        Link findOne = AuthorLinks.findOne(id);
        Link delete = AuthorLinks.delete(id);

        EntityModel<AuthorResponse> entityModel = EntityModel.of(response, selfLink, findOne, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
