package space.springbok.juniemvc.services;

import space.springbok.juniemvc.entities.Beer;
import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<Beer> listBeers();
    Optional<Beer> getBeerById(Integer id);
    Beer saveNewBeer(Beer beer);
    Optional<Beer> updateBeerById(Integer id, Beer beer);
    Boolean deleteBeerById(Integer id);
}
