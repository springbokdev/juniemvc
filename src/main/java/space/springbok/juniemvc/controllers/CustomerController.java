package space.springbok.juniemvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.exceptions.NotFoundException;
import space.springbok.juniemvc.models.CustomerDto;
import space.springbok.juniemvc.services.CustomerService;

import java.util.List;

/**
 * Controller for managing Customer operations.
 */
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
class CustomerController {

    private final CustomerService customerService;

    /**
     * List all customers.
     *
     * @return a list of all customers as CustomerDto.
     */
    @GetMapping
    List<CustomerDto> listCustomers() {
        return customerService.listCustomers();
    }

    /**
     * Get a customer by its ID.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the customer as CustomerDto if found.
     * @throws NotFoundException if the customer is not found.
     */
    @GetMapping("/{customerId}")
    CustomerDto getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    /**
     * Save a new customer.
     *
     * @param customer the CustomerDto object to save.
     * @return the saved CustomerDto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CustomerDto saveNewCustomer(@Valid @RequestBody CustomerDto customer) {
        return customerService.saveNewCustomer(customer);
    }

    /**
     * Update an existing customer by its ID.
     *
     * @param customerId the ID of the customer to update.
     * @param customer   the updated CustomerDto object.
     * @return the updated CustomerDto.
     * @throws NotFoundException if the customer to update is not found.
     */
    @PutMapping("/{customerId}")
    CustomerDto updateCustomerById(@PathVariable("customerId") Integer customerId, @Valid @RequestBody CustomerDto customer) {
        return customerService.updateCustomerById(customerId, customer).orElseThrow(NotFoundException::new);
    }

    /**
     * Delete a customer by its ID.
     *
     * @param customerId the ID of the customer to delete.
     * @throws NotFoundException if the customer to delete is not found.
     */
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        if (!customerService.deleteCustomerById(customerId)) {
            throw new NotFoundException();
        }
    }
}
