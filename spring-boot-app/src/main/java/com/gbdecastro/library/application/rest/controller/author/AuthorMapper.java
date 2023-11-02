package com.gbdecastro.library.application.rest.controller.author;

import com.gbdecastro.library.domain.author.Author;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toResponse(Author author);

    List<AuthorResponse> toResponseList(Collection<Author> author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author requestToAuthor(AuthorRequest authorRequest);

    @Named("responseToAuthor")
    @Mapping(target = "books", ignore = true)
    Author responseToAuthor(AuthorResponse response);

    @IterableMapping(qualifiedByName = "responseToAuthor")
    Set<Author> responseToAuthorList(Collection<AuthorResponse> response);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author update(@MappingTarget Author author, Author authorUpdate);

}
