package com.gbdecastro.library.application.rest.controller.book;

import com.gbdecastro.library.application.rest.controller.author.AuthorResponse;
import com.gbdecastro.library.domain.subject.Subject;
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
public class BookResponse {

    private Long id;
    private String title;
    private Integer edition;
    private String publicationYear;

    @Builder.Default
    private Set<AuthorResponse> authors = new HashSet<>();

    @Builder.Default
    private Set<Subject> subjects = new HashSet<>();
}
