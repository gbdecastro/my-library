package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.application.rest.controller.author.AuthorResponse;
import com.gbdecastro.library.application.rest.controller.subject.SubjectResponse;
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
    private Double price;
    private String publicationYear;

    private Set<AuthorResponse> authors;
    private Set<SubjectResponse> subjects;
}
