package com.example.travelagency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GuideNotFoundException extends RuntimeException {
    public GuideNotFoundException(Long id) {
        super("Could not find guide: " + id);
    }
}
