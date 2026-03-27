package space.springbok.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, Integer> {
}
