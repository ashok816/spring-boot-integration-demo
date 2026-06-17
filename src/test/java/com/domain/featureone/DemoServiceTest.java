package com.domain.featureone;

import com.domain.config.RestClientConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(DemoService.class)
@Import(RestClientConfig.class)
public class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    public void testGetCustomerDetails() throws JsonProcessingException {
        JsonMapper jsonMapper = new JsonMapper();
        CustomerDto customerDto = CustomerDto.builder().id(261).email("test.user@gmail.com")
                .name("Test User").phoneNumber("9100000001").build();

        mockServer.expect(requestTo("http://localhost:8080/api/customers/261"))
                .andRespond(withSuccess(jsonMapper.writeValueAsString(customerDto), MediaType.APPLICATION_JSON));

        CustomerDto result = demoService.getCustomerDetails(customerDto.getId());

        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
        assertEquals(customerDto.getEmail(), result.getEmail());
        assertEquals(customerDto.getName(), result.getName());
        assertEquals(customerDto.getPhoneNumber(), result.getPhoneNumber());
    }
}
