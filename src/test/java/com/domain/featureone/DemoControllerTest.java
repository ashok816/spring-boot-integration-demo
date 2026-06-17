package com.domain.featureone;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DemoService demoService;

    @Test
    public void testGetCustomerDetails() throws Exception {
        CustomerDto customerDto = CustomerDto.builder().id(261).email("test.user@gmail.com")
                                            .name("Test User").phoneNumber("9100000001").build();
        Mockito.when(demoService.getCustomerDetails(customerDto.getId())).thenReturn(customerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}", customerDto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()))
                .andExpect(jsonPath("$.name").value(customerDto.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(customerDto.getPhoneNumber()));
    }

}
