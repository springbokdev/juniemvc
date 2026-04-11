package space.springbok.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.springbok.juniemvc.entities.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("John Doe")
                .addressLine1("123 Main St")
                .city("Anytown")
                .state("NY")
                .postalCode("12345")
                .build());

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getCreatedDate()).isNotNull();
        assertThat(savedCustomer.getUpdateDate()).isNotNull();
    }

    @Test
    void testReadCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("John Doe")
                .addressLine1("123 Main St")
                .city("Anytown")
                .state("NY")
                .postalCode("12345")
                .build());

        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getName()).isEqualTo("John Doe");
    }

    @Test
    void testUpdateCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("John Doe")
                .addressLine1("123 Main St")
                .city("Anytown")
                .state("NY")
                .postalCode("12345")
                .build());

        savedCustomer.setName("Jane Doe");
        Customer updatedCustomer = customerRepository.save(savedCustomer);

        assertThat(updatedCustomer.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void testDeleteCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("John Doe")
                .addressLine1("123 Main St")
                .city("Anytown")
                .state("NY")
                .postalCode("12345")
                .build());

        customerRepository.delete(savedCustomer);

        assertThat(customerRepository.findById(savedCustomer.getId())).isEmpty();
    }
}
