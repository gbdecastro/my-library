package com.gbdecastro.library.application.rest.controller.subject;

import com.gbdecastro.library.application.rest.annotation.BaseController;
import com.gbdecastro.library.domain.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

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
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectResponse>> findOne(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<SubjectResponse>> create(SubjectRequest authorRequest) {
        return null;
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectResponse>> update(Long id, SubjectRequest authorRequest) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}
