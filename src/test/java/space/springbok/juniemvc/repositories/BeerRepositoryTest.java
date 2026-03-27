package space.springbok.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.springbok.juniemvc.entities.Beer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testReadBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        Beer foundBeer = beerRepository.findById(savedBeer.getId()).orElse(null);

        assertThat(foundBeer).isNotNull();
        assertThat(foundBeer.getBeerName()).isEqualTo("My Beer");
    }

    @Test
    void testUpdateBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        savedBeer.setBeerName("Updated Beer");
        Beer updatedBeer = beerRepository.save(savedBeer);

        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void testDeleteBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .price(new BigDecimal("11.99"))
                .build());

        beerRepository.delete(savedBeer);

        assertThat(beerRepository.findById(savedBeer.getId())).isEmpty();
    }
}
