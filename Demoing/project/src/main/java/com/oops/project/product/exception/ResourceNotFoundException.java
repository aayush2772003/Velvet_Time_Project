package com.oops.project.product.exception;

public class ResourceNotFoundException extends Exception {
 
    public ResourceNotFoundException() {
    }
 
    public ResourceNotFoundException(String msg) {
        super(msg);
    }    
}