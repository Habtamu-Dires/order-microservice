package com.hab.product_service.api_response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) { }
