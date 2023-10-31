package com.gbdecastro.library.domain.author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);

    List<Author> findAll();

    Author findById(Long id);

    Author update(Long id, Author author);

    void delete(Long id);
}
