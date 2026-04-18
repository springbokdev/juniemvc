package space.springbok.juniemvc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.models.BeerDto;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerServiceJPATest {

    @Autowired
    BeerService beerService;

    BeerDto testBeer;

    @BeforeEach
    void setUp() {
        testBeer = BeerDto.builder()
                .beerName("Service Test Beer")
                .beerStyle("PALE_ALE")
                .upc("123123")
                .price(new BigDecimal("9.99"))
                .build();
    }

    @Transactional
    @Test
    void listBeers() {
        beerService.saveNewBeer(testBeer);
        Page<BeerDto> beers = beerService.listBeers(null, null, null, null);
        assertThat(beers.getContent().size()).isGreaterThan(0);
    }

    @Transactional
    @Test
    void listBeersByName() {
        beerService.saveNewBeer(testBeer);
        Page<BeerDto> beers = beerService.listBeers("Service Test Beer", null, null, null);
        assertThat(beers.getContent().size()).isEqualTo(1);
    }

    @Transactional
    @Test
    void listBeersByStyle() {
        beerService.saveNewBeer(testBeer);
        Page<BeerDto> beers = beerService.listBeers(null, "PALE_ALE", null, null);
        assertThat(beers.getContent().size()).isEqualTo(1);
    }

    @Transactional
    @Test
    void getBeerById() {
        BeerDto savedBeer = beerService.saveNewBeer(testBeer);
        Optional<BeerDto> foundBeer = beerService.getBeerById(savedBeer.getId());
        assertThat(foundBeer).isPresent();
        assertThat(foundBeer.get().getId()).isEqualTo(savedBeer.getId());
    }

    @Transactional
    @Test
    void saveNewBeer() {
        BeerDto savedBeer = beerService.saveNewBeer(testBeer);
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertThat(savedBeer.getBeerName()).isEqualTo("Service Test Beer");
    }

    @Transactional
    @Test
    void updateBeerById() {
        BeerDto savedBeer = beerService.saveNewBeer(testBeer);
        BeerDto updateData = BeerDto.builder()
                .beerName("Updated Name")
                .beerStyle("IPA")
                .upc("999999")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(100)
                .build();

        Optional<BeerDto> updatedBeer = beerService.updateBeerById(savedBeer.getId(), updateData);

        assertThat(updatedBeer).isPresent();
        assertThat(updatedBeer.get().getBeerName()).isEqualTo("Updated Name");
        assertThat(updatedBeer.get().getBeerStyle()).isEqualTo("IPA");
        assertThat(updatedBeer.get().getUpc()).isEqualTo("999999");
    }

    @Transactional
    @Test
    void deleteBeerById() {
        BeerDto savedBeer = beerService.saveNewBeer(testBeer);
        Boolean deleted = beerService.deleteBeerById(savedBeer.getId());
        assertThat(deleted).isTrue();
        assertThat(beerService.getBeerById(savedBeer.getId())).isEmpty();
    }
}
