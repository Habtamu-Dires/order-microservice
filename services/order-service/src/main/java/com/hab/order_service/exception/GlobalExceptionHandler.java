package com.hab.order_service.exception;

import com.hab.order_service.api_response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handle(
            ResourceNotFoundException exp
    ){
         return ResponseEntity.badRequest()
                 .body(
                     ApiResponse.<String>builder()
                             .success(false)
                             .data(null)
                             .message(exp.getMessage())
                             .build()
                 );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handle(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError)error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                });

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<ErrorResponse>builder()
                                .success(true)
                                .data(new ErrorResponse(errors))
                                .message("Method Argument Exception")
                                .build()
                );

    }


}
