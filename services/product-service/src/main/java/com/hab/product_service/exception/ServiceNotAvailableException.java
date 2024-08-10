package com.hab.product_service.exception;

public class ServiceNotAvailableException extends RuntimeException{
    public ServiceNotAvailableException(String msg){
        super(msg);
    }
}
