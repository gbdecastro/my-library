package com.gbdecastro.library.application.rest.controller.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubjectResponse {

    private Long id;
    private String description;
}
