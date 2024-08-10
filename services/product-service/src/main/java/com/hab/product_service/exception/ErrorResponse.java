package com.hab.product_service.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String,Object> errors
) {
}
