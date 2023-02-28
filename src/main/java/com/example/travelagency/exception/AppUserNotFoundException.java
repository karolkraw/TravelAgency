package com.example.travelagency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(Long id) {
        super("Could not find user: " + id);
    }
}