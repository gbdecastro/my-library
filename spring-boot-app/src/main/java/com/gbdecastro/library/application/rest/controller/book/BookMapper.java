package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.domain.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {


    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(Collection<Book> book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    @Mapping(target = "bookSubjects", ignore = true)
    Book requestToBook(BookRequest bookRequest);

}
