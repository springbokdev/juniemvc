package space.springbok.juniemvc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.models.BeerOrderDto;
import space.springbok.juniemvc.models.BeerOrderLineDto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerOrderServiceJPATest {

    @Autowired
    BeerOrderService beerOrderService;

    @Autowired
    BeerService beerService;

    BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = beerService.saveNewBeer(BeerDto.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .price(new BigDecimal("12.99"))
                .upc("123456")
                .build());
    }

    @Transactional
    @Test
    void listOrders() {
        BeerOrderDto savedOrder = beerOrderService.saveNewOrder(BeerOrderDto.builder()
                .customerId("Test Customer")
                .paymentAmount(new BigDecimal("25.98"))
                .status("NEW")
                .build());

        List<BeerOrderDto> orders = beerOrderService.listOrders();
        assertThat(orders).isNotEmpty();
    }

    @Transactional
    @Test
    void saveNewOrder() {
        BeerOrderLineDto lineDto = BeerOrderLineDto.builder()
                .beerId(beerDto.getId())
                .orderQuantity(2)
                .build();

        BeerOrderDto orderDto = BeerOrderDto.builder()
                .customerId("Test Customer")
                .paymentAmount(new BigDecimal("25.98"))
                .status("NEW")
                .beerOrderLines(Collections.singleton(lineDto))
                .build();

        BeerOrderDto savedOrder = beerOrderService.saveNewOrder(orderDto);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getBeerOrderLines()).hasSize(1);
        assertThat(savedOrder.getBeerOrderLines().iterator().next().getBeerId()).isEqualTo(beerDto.getId());
    }

    @Transactional
    @Test
    void deleteOrderById() {
        BeerOrderDto savedOrder = beerOrderService.saveNewOrder(BeerOrderDto.builder()
                .customerId("Test Customer")
                .paymentAmount(new BigDecimal("25.98"))
                .status("NEW")
                .build());

        Boolean deleted = beerOrderService.deleteOrderById(savedOrder.getId());
        assertThat(deleted).isTrue();
        assertThat(beerOrderService.getOrderById(savedOrder.getId())).isEmpty();
    }
}
