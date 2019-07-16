package com.code4ro.legalconsultation.login.controller;

import com.code4ro.legalconsultation.common.controller.ExceptionResponse;
import com.code4ro.legalconsultation.common.controller.GlobalExceptionHandler;
import com.code4ro.legalconsultation.common.controller.I18nError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(final BadCredentialsException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setI18nErrors(Collections.singletonList(
                new I18nError("login.Bad.credentials", null)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
