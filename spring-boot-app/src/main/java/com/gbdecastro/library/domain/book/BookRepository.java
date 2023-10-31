package com.gbdecastro.library.domain.book;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface BookRepository extends JpaRepository<Book, Long> {
}
