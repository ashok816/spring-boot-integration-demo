package com.domain.featureone.integration;

import com.domain.featureone.CustomerDto;
import com.domain.featureone.DemoService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoService demoService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        // Create RestTemplate and bind MockRestServiceServer
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        // Rebuild RestClient with mock-bound request factory
        RestClient mockBoundClient = RestClient.builder()
                .requestFactory(restTemplate.getRequestFactory())
                .baseUrl("http://localhost:8080")
                .build();

        // Inject mock-bound RestClient into DemoService
        ReflectionTestUtils.setField(demoService, "restClient", mockBoundClient);
    }

    @Test
    public void testGetCustomerDetails() throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        CustomerDto customerDto = CustomerDto.builder().id(261).email("test.user@gmail.com")
                .name("Test User").phoneNumber("9100000001").build();

        mockServer.expect(requestTo("http://localhost:8080/api/customers/261"))
                .andRespond(withSuccess(jsonMapper.writeValueAsString(customerDto), MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}", customerDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$.name").value(customerDto.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(customerDto.getPhoneNumber()));
    }
}
