package com.gbdecastro.library.domain.author;

import java.util.List;
import java.util.Set;

public interface AuthorService {
    Author create(Author author);

    List<Author> findAll();

    Set<Author> findAllOrCreate(Set<Author> authors);

    Author findById(Long id);

    Author update(Long id, Author author);

    void delete(Long id);
}
