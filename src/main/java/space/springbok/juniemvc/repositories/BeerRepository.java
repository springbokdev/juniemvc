package space.springbok.juniemvc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import space.springbok.juniemvc.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, Integer> {
    Page<Beer> findAllByBeerNameLikeIgnoreCase(String beerName, Pageable pageable);
}
