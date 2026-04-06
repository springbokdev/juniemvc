package space.springbok.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.BeerOrderLine;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, Integer> {
}
