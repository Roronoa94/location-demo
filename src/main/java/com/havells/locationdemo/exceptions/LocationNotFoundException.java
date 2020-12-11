package com.havells.locationdemo.exceptions;

import java.text.MessageFormat;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(Long id) {
        super(MessageFormat.format("Location with id {0} : Not found", id));
    }
}
