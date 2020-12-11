package com.havells.locationdemo.controllers;

import com.havells.locationdemo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity handleLocationNotFoundException() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
