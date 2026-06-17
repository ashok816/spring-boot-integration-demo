package com.domain.featureone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final DemoService demoService;

    // Based on the profile we set using the environment variable "spring.profiles.active" the appropriate application.yml file will get loaded
    @Value("${environment.name}")
    private String envName;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public ResponseEntity<String> getCurrentEnvironment() {
        return ResponseEntity.ok("We are in " + envName + " environment");
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable Integer customerId) {
        CustomerDto customerDto = demoService.getCustomerDetails(customerId);
        return ResponseEntity.ok(customerDto);
    }
}
