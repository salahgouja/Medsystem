package com.salah.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static com.salah.exception.BusinessErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleIllegalStateException(IllegalStateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder()
                        .businessErrorCode(ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                        .error(exp.getMessage())
                        .build()) ;
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder()
                        .businessErrorCode(ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build()) ;
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                        .error(BAD_CREDENTIALS.getDescription())
                        .build()) ;
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .error(exp.getMessage())
                        .build()) ;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {

        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });

        return ResponseEntity.status(BAD_REQUEST)
                        .body( ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build() );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {

        exp.printStackTrace();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                        .businessErrorDescription("INTERNAL_SERVER_ERROR , contact admin")
                                .error(exp.getMessage())
                                .build() );
    }





}
