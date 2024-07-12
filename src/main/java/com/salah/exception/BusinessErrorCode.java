package com.salah.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor

public enum BusinessErrorCode {
    No_code(0,NOT_IMPLEMENTED,"no code "),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST,"INCORRECT_CURRENT_PASSWORD "),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST,"NEW_PASSWORD_DOES_NOT_MATCH "),


    ACCOUNT_LOCKED(302,FORBIDDEN,"user account is locked "),
    ACCOUNT_DISABLED(303,FORBIDDEN,"user account is DISABLED "),
    BAD_CREDENTIALS(304,FORBIDDEN,"Login and/or password incorrect"),


    ;


    @Getter
    private final int code ;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description ;


}
