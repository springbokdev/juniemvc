package space.springbok.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.mappers.BeerMapper;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.repositories.BeerRepository;

import java.util.Optional;

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
    public Page<BeerDto> listBeers(String beerName, String beerStyle, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : 25);
        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
        } else if (StringUtils.hasText(beerName) && !StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameLikeIgnoreCase("%" + beerName + "%", pageable);
        } else if (!StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageable);
        } else {
            beerPage = beerRepository.findAll(pageable);
        }

        return beerPage.map(beerMapper::beerToBeerDto);
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
            beerMapper.updateBeerFromDto(beerDto, existingBeer);
            return beerMapper.beerToBeerDto(beerRepository.saveAndFlush(existingBeer));
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
