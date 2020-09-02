package com.axgrid.state.exceptions;

public class AxStateNotFoundException extends RuntimeException {
    public AxStateNotFoundException(long id, String className) {
        super(String.format("State %s.[%d] not found", className, id));
    }
}
