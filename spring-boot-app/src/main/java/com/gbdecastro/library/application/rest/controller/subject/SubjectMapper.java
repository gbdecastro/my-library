package com.gbdecastro.library.application.rest.controller.subject;

import com.gbdecastro.library.domain.subject.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "books", ignore = true)
    SubjectResponse toResponse(Subject subject);

    List<SubjectResponse> toResponseList(Collection<Subject> subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookSubjects", ignore = true)
    Subject requestToSubject(SubjectRequest subjectRequest);

}
