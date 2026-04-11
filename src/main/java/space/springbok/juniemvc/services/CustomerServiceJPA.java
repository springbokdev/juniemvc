package space.springbok.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.entities.Customer;
import space.springbok.juniemvc.mappers.CustomerMapper;
import space.springbok.juniemvc.models.CustomerDto;
import space.springbok.juniemvc.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of CustomerService.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDto> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    @Transactional
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDto));
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> updateCustomerById(Integer id, CustomerDto customerDto) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
            existingCustomer.setAddressLine1(customerDto.getAddressLine1());
            existingCustomer.setAddressLine2(customerDto.getAddressLine2());
            existingCustomer.setCity(customerDto.getCity());
            existingCustomer.setState(customerDto.getState());
            existingCustomer.setPostalCode(customerDto.getPostalCode());
            return customerMapper.customerToCustomerDto(customerRepository.save(existingCustomer));
        });
    }

    @Override
    @Transactional
    public Boolean deleteCustomerById(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
