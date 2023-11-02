package com.gbdecastro.library.domain.subject;

import java.util.List;
import java.util.Set;

public interface SubjectService {
    List<Subject> findAll();

    Set<Subject> findAllOrCreate(Set<Subject> subjects);

    Subject findById(Long id);

    Subject create(Subject subject);

    Subject update(Long id, Subject subject);

    void delete(Long id);
}
