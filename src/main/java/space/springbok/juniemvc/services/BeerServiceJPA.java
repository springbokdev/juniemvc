package space.springbok.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.mappers.BeerMapper;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of BeerService.
 */
@Service
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BeerDto> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerDto> getBeerById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    @Transactional
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    @Transactional
    public Optional<BeerDto> updateBeerById(Integer id, BeerDto beerDto) {
        return beerRepository.findById(id).map(existingBeer -> {
            existingBeer.setBeerName(beerDto.beerName());
            existingBeer.setBeerStyle(beerDto.beerStyle());
            existingBeer.setUpc(beerDto.upc());
            existingBeer.setPrice(beerDto.price());
            existingBeer.setQuantityOnHand(beerDto.quantityOnHand());
            return beerMapper.beerToBeerDto(beerRepository.save(existingBeer));
        });
    }

    @Override
    @Transactional
    public Boolean deleteBeerById(Integer id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
