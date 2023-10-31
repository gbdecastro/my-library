package com.gbdecastro.library.application.rest.shared.annotations;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest
@NullAndEmptySource()
@ValueSource(strings = {"", " ", "\t", "\n"})
public @interface ParameterizedStringTest {
}
