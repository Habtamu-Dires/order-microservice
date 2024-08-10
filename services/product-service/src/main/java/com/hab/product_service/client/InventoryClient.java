package com.hab.product_service.client;

import com.hab.product_service.api_response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.service.annotation.GetExchange;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.inventory-url}")
    private String base_url;

    @Value("${spring.security.oauth2.client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.access-token-uri}")
    private String tokenUri;

    private String accessToken;
    private Instant tokenExpirationTime;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    //@CircuitBreakerAndRetry
    public ApiResponse<List<InventoryResponse>> getAvailableInventory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken()); // Add the token dynamically

        HttpEntity<String> entity = new HttpEntity<>(headers);

        var responseType =
                new ParameterizedTypeReference<ApiResponse<List<InventoryResponse>>>() {};

        ResponseEntity<ApiResponse<List<InventoryResponse>>>response = restTemplate.exchange(
                base_url + "/api/v1/inventory/available",
                HttpMethod.GET,
                entity,
                responseType
        );


        return response.getBody();
    }

    public ApiResponse<Boolean> fallbackMethod(Throwable throwable) {
        var msg = String.format("Inventory Service not available, Cannot get inventory for skucode failure reason: %s"
                ,  throwable.getMessage()
        );
        System.out.println(msg);
        return ApiResponse.<Boolean>builder()
                .data(false)
                .success(false)
                .message(msg)
                .build();
    }



    private synchronized String getAccessToken() {
        if (accessToken == null || tokenExpirationTime == null ||
                Instant.now().isAfter(tokenExpirationTime)) {

            refreshToken();
        }
        return accessToken;
    }

    private void refreshToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        this.accessToken = (String) responseBody.get("access_token");
        int expiresIn = (Integer) responseBody.get("expires_in");

        this.tokenExpirationTime = Instant.now()
                .plusSeconds(expiresIn - 60); // Refresh token 1 minute before expiration
    }

}
