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
}
