package com.gbdecastro.library.domain.subject;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
