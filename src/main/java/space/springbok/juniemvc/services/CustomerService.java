package space.springbok.juniemvc.services;

import space.springbok.juniemvc.models.CustomerDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Customer operations.
 */
public interface CustomerService {
    List<CustomerDto> listCustomers();

    Optional<CustomerDto> getCustomerById(Integer id);

    CustomerDto saveNewCustomer(CustomerDto customer);

    Optional<CustomerDto> updateCustomerById(Integer id, CustomerDto customer);

    Boolean deleteCustomerById(Integer id);
}
