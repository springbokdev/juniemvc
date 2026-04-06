package space.springbok.juniemvc.services;

import space.springbok.juniemvc.models.BeerDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Beer operations.
 */
public interface BeerService {
    List<BeerDto> listBeers();

    Optional<BeerDto> getBeerById(Integer id);

    BeerDto saveNewBeer(BeerDto beer);

    Optional<BeerDto> updateBeerById(Integer id, BeerDto beer);

    Boolean deleteBeerById(Integer id);
}
