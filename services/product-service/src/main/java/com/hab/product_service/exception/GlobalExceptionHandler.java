package com.hab.product_service.exception;

import com.hab.product_service.api_response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handle(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String,Object>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var filename = ((FieldError)error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(filename, errorMessage);
                });
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(
                        false,
                        new ErrorResponse(errors),
                        "MethodArgumentException , for more detail see the data"
                ));
    }

    @ExceptionHandler(ServiceNotAvailableException.class)
    public ResponseEntity<ApiResponse<String>> handle(
            ServiceNotAvailableException exp
    ) {

        return ResponseEntity.badRequest()
                .body(ApiResponse.<String>builder()
                        .success(false)
                        .data(null)
                        .message(exp.getMessage())
                        .build()
                );
    }

}
