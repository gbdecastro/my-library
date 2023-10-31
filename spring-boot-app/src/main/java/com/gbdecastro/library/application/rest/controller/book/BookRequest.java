package com.gbdecastro.library.application.rest.controller.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookRequest {

    private String title;
    private Integer edition;
    private String publicationYear;

    private Set<Long> authors;
    private Set<Long> subjects;
}
