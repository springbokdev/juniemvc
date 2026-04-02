package space.springbok.juniemvc.services;

import lombok.RequiredArgsConstructor;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.repositories.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;

    @Override
    public List<Beer> listBeers() {
        return beerRepository.findAll();
    }

    @Override
    public Optional<Beer> getBeerById(Integer id) {
        return beerRepository.findById(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public Optional<Beer> updateBeerById(Integer id, Beer beer) {
        return beerRepository.findById(id).map(existingBeer -> {
            existingBeer.setBeerName(beer.getBeerName());
            existingBeer.setBeerStyle(beer.getBeerStyle());
            existingBeer.setUpc(beer.getUpc());
            existingBeer.setPrice(beer.getPrice());
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            return beerRepository.save(existingBeer);
        });
    }

    @Override
    public Boolean deleteBeerById(Integer id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
