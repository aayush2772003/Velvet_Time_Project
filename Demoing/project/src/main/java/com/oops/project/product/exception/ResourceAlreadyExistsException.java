package com.oops.project.product.exception;

public class ResourceAlreadyExistsException extends Exception {
 
    public ResourceAlreadyExistsException() {
    }
 
    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}