package com.gbdecastro.library.application.rest.controller.author;

import com.gbdecastro.library.domain.author.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true)
    AuthorResponse toResponse(Author author);

    List<AuthorResponse> toResponseList(Collection<Author> author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    Author requestToAuthor(AuthorRequest authorRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    Author update(@MappingTarget Author author, Author authorUpdate);

}
