package space.springbok.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.BeerOrder;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, Integer> {
}
