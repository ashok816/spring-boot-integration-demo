package com.domain.featureone;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DemoService {

    private final RestClient restClient;

    public DemoService(RestClient restClient) {
        this.restClient = restClient;
    }

    public CustomerDto getCustomerDetails(Integer customerId) {

        /* Get JWT Token in Controller from @AuthenticationPrincipal Jwt jwt and then
         Pass that Token via rest client AUTHORIZATION header in this API Call. */
        return restClient.get()
                .uri("/api/customers/{id}", customerId)
                .retrieve()
                .body(CustomerDto.class);
    }
}
