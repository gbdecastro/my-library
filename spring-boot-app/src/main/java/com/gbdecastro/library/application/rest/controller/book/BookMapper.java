package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.domain.book.Book;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Named("toResponse")
    @Mapping(target = "authors", source = "authors")
    BookResponse toResponse(Book book);

    @IterableMapping(qualifiedByName = "toResponse")
    List<BookResponse> toResponseList(Collection<Book> book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    Book requestToBook(BookRequest bookRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book update(@MappingTarget Book book, BookRequest bookRequest);

}
