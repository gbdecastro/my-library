package com.gbdecastro.library.application.rest.controller.author;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public abstract class AuthorLinks {

    public static final Class<AuthorController> controller = AuthorController.class;

    public static Link findAll() {
        return linkTo(methodOn(controller).findAll()).withRel("find-all");
    }

    public static Link findOne(Long id) {
        return linkTo(methodOn(controller).findOne(id)).withRel("find-one");
    }

    public static Link create(AuthorRequest authorRequest) {
        return linkTo(methodOn(controller).create(authorRequest)).withRel("create");
    }

    public static Link update(Long id, AuthorRequest authorRequest) {
        return linkTo(methodOn(controller).update(id, authorRequest)).withRel("update");
    }

    public static Link delete(Long id) {
        return linkTo(methodOn(controller).delete(id)).withRel("delete");
    }

}
