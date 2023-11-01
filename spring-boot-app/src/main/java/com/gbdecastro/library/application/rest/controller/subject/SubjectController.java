package com.gbdecastro.library.application.rest.controller.subject;

import com.gbdecastro.library.application.rest.annotation.BaseController;
import com.gbdecastro.library.domain.subject.Subject;
import com.gbdecastro.library.domain.subject.SubjectService;
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
import javax.transaction.Transactional;

@BaseController(SubjectController.API_URL)
public class SubjectController implements SubjectApi {
    public static final String API_URL = "/api/v1/subjects";

    @Autowired
    private SubjectService service;

    @Autowired
    private SubjectMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<SubjectResponse>> findAll() {
        List<Subject> subjects = service.findAll();

        List<SubjectResponse> response = mapper.toResponseList(subjects);

        Link self = linkTo(methodOn(getClass()).findAll()).withSelfRel();
        Link create = SubjectLinks.create(null);
        Link update = SubjectLinks.update(null, null);
        Link delete = SubjectLinks.delete(null);
        Link findOne = SubjectLinks.findOne(null);

        CollectionModel<SubjectResponse> entityModel = CollectionModel.of(response, self, create, update, delete, findOne);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectResponse>> findOne(Long id) {
        SubjectResponse response = mapper.toResponse(service.findById(id));

        Link self = linkTo(methodOn(getClass()).findOne(id)).withSelfRel();
        Link update = SubjectLinks.update(id, null);
        Link delete = SubjectLinks.delete(id);

        EntityModel<SubjectResponse> entityModel = EntityModel.of(response, self, update, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<SubjectResponse>> create(@Valid @RequestBody SubjectRequest subjectRequest) {
        Subject subject = this.mapper.requestToSubject(subjectRequest);
        SubjectResponse response = this.mapper.toResponse(this.service.create(subject));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        Link selfLink = linkTo(methodOn(getClass()).create(subjectRequest)).withSelfRel();
        Link findOne = SubjectLinks.findOne(response.getId());
        Link update = SubjectLinks.update(response.getId(), null);
        Link delete = SubjectLinks.delete(response.getId());


        EntityModel<SubjectResponse> entityModel = EntityModel.of(response, selfLink, findOne, update, delete);

        return ResponseEntity.created(location).body(entityModel);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectResponse>> update(Long id, @Valid @RequestBody SubjectRequest subjectRequest) {
        SubjectResponse response = mapper.toResponse(service.update(id, mapper.requestToSubject(subjectRequest)));

        Link selfLink = linkTo(methodOn(getClass()).update(id, subjectRequest)).withSelfRel();
        Link findOne = SubjectLinks.findOne(id);
        Link delete = SubjectLinks.delete(id);

        EntityModel<SubjectResponse> entityModel = EntityModel.of(response, selfLink, findOne, delete);

        return ResponseEntity.ok(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    @Transactional()
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
