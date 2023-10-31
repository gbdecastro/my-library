package com.gbdecastro.library.domain.author;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
