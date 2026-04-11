package space.springbok.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.springbok.juniemvc.models.CustomerDto;
import space.springbok.juniemvc.services.CustomerService;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    CustomerDto testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerDto.builder()
                .id(1)
                .name("John Doe")
                .addressLine1("123 Main St")
                .city("Anytown")
                .state("NY")
                .postalCode("12345")
                .build();
    }

    @Test
    void listCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(Collections.singletonList(testCustomer));

        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    void getCustomerById() throws Exception {
        given(customerService.getCustomerById(any(Integer.class))).willReturn(Optional.of(testCustomer));

        mockMvc.perform(get("/api/v1/customer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/customer/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveNewCustomer() throws Exception {
        CustomerDto newCustomer = CustomerDto.builder()
                .name("Jane Smith")
                .addressLine1("456 Oak Ave")
                .city("Othertown")
                .state("CA")
                .postalCode("67890")
                .build();
        CustomerDto savedCustomer = CustomerDto.builder()
                .id(1)
                .name("Jane Smith")
                .addressLine1("456 Oak Ave")
                .city("Othertown")
                .state("CA")
                .postalCode("67890")
                .build();

        given(customerService.saveNewCustomer(any(CustomerDto.class))).willReturn(savedCustomer);

        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jane Smith")));
    }

    @Test
    void updateCustomerById() throws Exception {
        given(customerService.updateCustomerById(any(Integer.class), any(CustomerDto.class))).willReturn(Optional.of(testCustomer));

        mockMvc.perform(put("/api/v1/customer/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));

        verify(customerService).updateCustomerById(any(Integer.class), any(CustomerDto.class));
    }

    @Test
    void deleteCustomerById() throws Exception {
        given(customerService.deleteCustomerById(any(Integer.class))).willReturn(true);

        mockMvc.perform(delete("/api/v1/customer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(any(Integer.class));
    }
}
