package com.hab.order_service.dto;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record UserDetails(
        @NotNull
        String firstname,
        @NotNull
        String lastname,
        @NotNull
        String email
) {
}
