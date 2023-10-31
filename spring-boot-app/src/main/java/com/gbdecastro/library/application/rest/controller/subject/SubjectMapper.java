package com.gbdecastro.library.application.rest.controller.subject;

import com.gbdecastro.library.domain.subject.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponse toResponse(Subject subject);

    List<SubjectResponse> toResponseList(Collection<Subject> subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Subject requestToSubject(SubjectRequest subjectRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Subject update(@MappingTarget Subject subject, Subject toUpdate);

}
