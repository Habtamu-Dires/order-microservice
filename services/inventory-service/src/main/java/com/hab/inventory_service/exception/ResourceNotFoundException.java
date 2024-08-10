package com.hab.inventory_service.exception;

import lombok.Data;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
