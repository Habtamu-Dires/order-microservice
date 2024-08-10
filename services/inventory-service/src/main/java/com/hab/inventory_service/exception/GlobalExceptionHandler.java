package com.hab.inventory_service.exception;

import com.hab.inventory_service.api_response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handle(
            ResourceNotFoundException exp
    ){
        log.info("throwing exception {} ",exp.getMessage());
        return ResponseEntity.ok()
                .body(
                    ApiResponse.builder()
                        .success(false)
                        .data(null)
                        .message(exp.getMessage())
                        .build()
                );
    }
}
