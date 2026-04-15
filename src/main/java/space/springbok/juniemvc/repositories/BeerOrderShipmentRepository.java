package space.springbok.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.BeerOrderShipment;

/**
 * Repository for BeerOrderShipment entities.
 */
public interface BeerOrderShipmentRepository extends JpaRepository<BeerOrderShipment, Integer> {
}
