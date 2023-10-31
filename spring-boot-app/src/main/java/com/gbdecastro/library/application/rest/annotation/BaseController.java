package com.gbdecastro.library.application.rest.annotation;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Lazy
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping
public @interface BaseController {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String value() default "/api/";
}
