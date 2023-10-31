package com.gbdecastro.library.application.rest.controller.author;

import com.gbdecastro.library.application.rest.controller.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthorResponse {

    private Long id;
    private String name;

    @Builder.Default
    private Set<BookResponse> books = new HashSet<>();
}