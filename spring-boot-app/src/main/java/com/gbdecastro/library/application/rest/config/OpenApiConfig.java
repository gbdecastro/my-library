package com.gbdecastro.library.application.rest.config;

import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class OpenApiConfig {

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {

            Parameter acceptLanguage = new HeaderParameter().in("header").required(true).description("The desired language for the response.")
                .name("Accept-Language").schema(new StringSchema().addEnumItem(Locale.ENGLISH.getLanguage()).addEnumItem(new Locale("pt").getLanguage()));
            operation.addParametersItem(acceptLanguage);
            return operation;
        };
    }
}
