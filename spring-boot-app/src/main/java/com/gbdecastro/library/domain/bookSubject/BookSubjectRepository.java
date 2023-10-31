package com.gbdecastro.library.domain.bookSubject;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface BookSubjectRepository extends JpaRepository<BookSubject, Long> {
}
