package com.gbdecastro.library.application.rest.hander;


import com.gbdecastro.library.application.rest.hander.exception.AccessDeniedForActionException;
import com.gbdecastro.library.application.rest.hander.exception.AuthorizationException;
import com.gbdecastro.library.domain.shared.DomainException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.ERROR_403_DEFAULT;
import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.ERROR_409_DEFAULT;
import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.ERROR_500_DEFAULT;
import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.ERROR_DATA_INCORRECT_DEFAULT;
import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.INVALID_TOKEN_ACCESS;
import static com.gbdecastro.library.application.rest.constant.ApiRestConstant.JSON_DATA_INVALID;

@ControllerAdvice
public class ApiExceptionResponse extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleInternal(final Exception ex) {
        return generatedError(ERROR_500_DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResponse> handleAccessDeniedException(final AccessDeniedException ex) {
        return generatedError(ERROR_403_DEFAULT, HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiResponse> handleEntityNotFoundException(final EntityNotFoundException ex) {
        return generatedError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex);
    }

    @ExceptionHandler({AccessDeniedForActionException.class})
    public ResponseEntity<ApiResponse> handleAccessDeniedForActionException(final AccessDeniedForActionException ex) {
        String msg = (ex.getMessage() == null || ex.getMessage().isEmpty()) ? ERROR_403_DEFAULT : ex.getMessage();
        return generatedError(msg, HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler({DataAccessException.class, HibernateException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ApiResponse> handleConflict(final DataAccessException ex) {
        return generatedError(ERROR_409_DEFAULT, HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<ApiResponse> handleAccessDeniedException(final AuthorizationException ex) {
        return generatedError(INVALID_TOKEN_ACCESS, HttpStatus.UNAUTHORIZED, ex);
    }


    @ExceptionHandler({HttpMessageConversionException.class})
    public ResponseEntity<ApiResponse> handleHttpMessageConversionException(final RuntimeException ex) {
        return generatedError(JSON_DATA_INVALID, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({DomainException.class})
    public ResponseEntity<ApiResponse> handleDomainException(final DomainException ex) {
        return generatedError(ex.getCode(), ex.getMessage(), ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        return handleExceptionInternal(ex, new ApiResponse(status.value(), ERROR_DATA_INCORRECT_DEFAULT), headers, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ApiResponse> generatedError(String message, HttpStatus http, Exception ex) {
        System.err.println(http.value() + ": " + ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(http.value(), message), http);
    }

    private ResponseEntity<ApiResponse> generatedError(int code, String message, Exception ex) {
        System.err.println(code + ": " + ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(code, message), HttpStatus.valueOf(code));
    }
}
