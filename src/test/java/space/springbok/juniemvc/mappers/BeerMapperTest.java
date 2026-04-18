package space.springbok.juniemvc.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.models.BeerPatchDto;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerMapperTest {

    @Autowired
    BeerMapper beerMapper;

    @Test
    void patchBeerFromDto() {
        Beer beer = Beer.builder()
                .beerName("Original Name")
                .beerStyle("Original Style")
                .upc("123456")
                .price(new BigDecimal("10.00"))
                .quantityOnHand(10)
                .build();

        BeerPatchDto patchDto = BeerPatchDto.builder()
                .beerName("Updated Name")
                .build();

        beerMapper.patchBeerFromDto(patchDto, beer);

        assertThat(beer.getBeerName()).isEqualTo("Updated Name");
        assertThat(beer.getBeerStyle()).isEqualTo("Original Style");
        assertThat(beer.getUpc()).isEqualTo("123456");
        assertThat(beer.getPrice()).isEqualByComparingTo("10.00");
        assertThat(beer.getQuantityOnHand()).isEqualTo(10);
    }
}
