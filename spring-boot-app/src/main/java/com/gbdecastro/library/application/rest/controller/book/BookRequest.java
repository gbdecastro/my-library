package com.gbdecastro.library.application.rest.controller.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookRequest {

    @NotEmpty(message = "{name_max_length}")
    @Size(max = 40, message = "{name_max_length}")
    private String title;

    @NotEmpty()
    private Integer edition;

    @NotEmpty(message = "{name_max_length}")
    @Size(max = 4, message = "{name_max_length}")
    private String publicationYear;

    @NotEmpty()
    private Set<Long> authors;

    @NotEmpty()
    private Set<Long> subjects;
}
