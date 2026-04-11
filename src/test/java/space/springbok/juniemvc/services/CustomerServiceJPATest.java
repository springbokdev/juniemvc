package space.springbok.juniemvc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.models.CustomerDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceJPATest {

    @Autowired
    CustomerService customerService;

    CustomerDto testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = CustomerDto.builder()
                .name("Service Test Customer")
                .addressLine1("123 Service St")
                .city("Service City")
                .state("SC")
                .postalCode("54321")
                .build();
    }

    @Transactional
    @Test
    void listCustomers() {
        customerService.saveNewCustomer(testCustomer);
        List<CustomerDto> customers = customerService.listCustomers();
        assertThat(customers.size()).isGreaterThan(0);
    }

    @Transactional
    @Test
    void getCustomerById() {
        CustomerDto savedCustomer = customerService.saveNewCustomer(testCustomer);
        Optional<CustomerDto> foundCustomer = customerService.getCustomerById(savedCustomer.getId());
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getId()).isEqualTo(savedCustomer.getId());
    }

    @Transactional
    @Test
    void saveNewCustomer() {
        CustomerDto savedCustomer = customerService.saveNewCustomer(testCustomer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("Service Test Customer");
    }

    @Transactional
    @Test
    void updateCustomerById() {
        CustomerDto savedCustomer = customerService.saveNewCustomer(testCustomer);
        CustomerDto updateData = CustomerDto.builder()
                .name("Updated Name")
                .addressLine1("456 Updated Ave")
                .city("Updated City")
                .state("UC")
                .postalCode("99999")
                .build();

        Optional<CustomerDto> updatedCustomer = customerService.updateCustomerById(savedCustomer.getId(), updateData);

        assertThat(updatedCustomer).isPresent();
        assertThat(updatedCustomer.get().getName()).isEqualTo("Updated Name");
        assertThat(updatedCustomer.get().getAddressLine1()).isEqualTo("456 Updated Ave");
    }

    @Transactional
    @Test
    void deleteCustomerById() {
        CustomerDto savedCustomer = customerService.saveNewCustomer(testCustomer);
        Boolean deleted = customerService.deleteCustomerById(savedCustomer.getId());
        assertThat(deleted).isTrue();
        assertThat(customerService.getCustomerById(savedCustomer.getId())).isEmpty();
    }
}
