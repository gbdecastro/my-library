package com.gbdecastro.library.application.rest.controller.book;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public abstract class BookLinks {

    public static final Class<BookController> controller = BookController.class;

    public static Link findAll() {
        return linkTo(methodOn(controller).findAll()).withRel("find-all");
    }

    public static Link findOne(Long id) {
        return linkTo(methodOn(controller).findOne(id)).withRel("find-one");
    }

    public static Link create(BookRequest bookRequest) {
        return linkTo(methodOn(controller).create(bookRequest)).withRel("create");
    }

    public static Link update(Long id, BookRequest bookRequest) {
        return linkTo(methodOn(controller).update(id, bookRequest)).withRel("update");
    }

    public static Link delete(Long id) {
        return linkTo(methodOn(controller).delete(id)).withRel("delete");
    }

}
