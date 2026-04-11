package space.springbok.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.Customer;

/**
 * Repository for Customer operations.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
