package space.springbok.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.entities.BeerOrder;
import space.springbok.juniemvc.entities.BeerOrderLine;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveOrder() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .price(new BigDecimal("12.99"))
                .upc("123456")
                .build());

        BeerOrder beerOrder = BeerOrder.builder()
                .paymentAmount(new BigDecimal("25.98"))
                .status("NEW")
                .build();

        BeerOrderLine line = BeerOrderLine.builder()
                .beer(beer)
                .orderQuantity(2)
                .build();

        beerOrder.addOrderLine(line);

        BeerOrder savedOrder = beerOrderRepository.save(beerOrder);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getBeerOrderLines()).hasSize(1);
        assertThat(savedOrder.getBeerOrderLines().iterator().next().getBeer().getId()).isEqualTo(beer.getId());
    }
}
