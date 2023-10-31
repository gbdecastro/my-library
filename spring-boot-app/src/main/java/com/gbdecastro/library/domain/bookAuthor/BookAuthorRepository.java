package com.gbdecastro.library.domain.bookAuthor;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
