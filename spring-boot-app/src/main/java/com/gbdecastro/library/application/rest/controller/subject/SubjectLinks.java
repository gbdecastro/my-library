package com.gbdecastro.library.application.rest.controller.subject;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public abstract class SubjectLinks {

    public static final Class<SubjectController> controller = SubjectController.class;

    public static Link findAll() {
        return linkTo(methodOn(controller).findAll()).withRel("find-all");
    }

    public static Link findOne(Long id) {
        return linkTo(methodOn(controller).findOne(id)).withRel("find-one");
    }

    public static Link create(SubjectRequest subjectRequest) {
        return linkTo(methodOn(controller).create(subjectRequest)).withRel("create");
    }

    public static Link update(Long id, SubjectRequest subjectRequest) {
        return linkTo(methodOn(controller).update(id, subjectRequest)).withRel("update");
    }

    public static Link delete(Long id) {
        return linkTo(methodOn(controller).delete(id)).withRel("delete");
    }

}
