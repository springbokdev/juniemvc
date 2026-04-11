package space.springbok.juniemvc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.models.CustomerDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    CustomerDto testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerDto.builder()
                .name("IT Test Customer")
                .addressLine1("IT Lane")
                .city("IT City")
                .state("IT")
                .postalCode("IT123")
                .build();
    }

    @Test
    void testListCustomers() {
        ResponseEntity<CustomerDto[]> response = restTemplate.getForEntity("/api/v1/customer", CustomerDto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // We might have seed data or data from other tests if not transactional, but it should be OK
    }

    @Test
    void testSaveAndGetCustomer() {
        ResponseEntity<CustomerDto> response = restTemplate.postForEntity("/api/v1/customer", testCustomer, CustomerDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        Integer id = response.getBody().getId();

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity("/api/v1/customer/" + id, CustomerDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("IT Test Customer");
    }

    @Test
    void testUpdateCustomer() {
        ResponseEntity<CustomerDto> response = restTemplate.postForEntity("/api/v1/customer", testCustomer, CustomerDto.class);
        Integer id = response.getBody().getId();

        testCustomer.setName("Updated IT Name");

        restTemplate.put("/api/v1/customer/" + id, testCustomer);

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity("/api/v1/customer/" + id, CustomerDto.class);
        assertThat(getResponse.getBody().getName()).isEqualTo("Updated IT Name");
    }

    @Test
    void testDeleteCustomer() {
        ResponseEntity<CustomerDto> response = restTemplate.postForEntity("/api/v1/customer", testCustomer, CustomerDto.class);
        Integer id = response.getBody().getId();

        restTemplate.delete("/api/v1/customer/" + id);

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity("/api/v1/customer/" + id, CustomerDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
