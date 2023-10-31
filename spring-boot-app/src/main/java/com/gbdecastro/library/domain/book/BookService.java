package com.gbdecastro.library.domain.book;

import com.gbdecastro.library.application.rest.controller.book.BookRequest;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    Book create(BookRequest bookRequest);

    void delete(Long id);

    Book update(Long id, BookRequest bookRequest);
}
