package com.gbdecastro.library.application.rest.hander;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
}
