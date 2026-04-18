package space.springbok.juniemvc.services;

import org.springframework.data.domain.Page;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.models.BeerPatchDto;

import java.util.Optional;

/**
 * Service interface for managing Beer operations.
 */
public interface BeerService {
    Page<BeerDto> listBeers(String beerName, String beerStyle, Integer pageNumber, Integer pageSize);

    Optional<BeerDto> getBeerById(Integer id);

    BeerDto saveNewBeer(BeerDto beer);

    Optional<BeerDto> updateBeerById(Integer id, BeerDto beer);

    Optional<BeerDto> patchBeerById(Integer id, BeerPatchDto beer);

    Boolean deleteBeerById(Integer id);
}
