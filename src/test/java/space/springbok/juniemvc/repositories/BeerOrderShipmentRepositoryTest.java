package space.springbok.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.entities.BeerOrder;
import space.springbok.juniemvc.entities.BeerOrderShipment;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerOrderShipmentRepositoryTest {

    @Autowired
    BeerOrderShipmentRepository shipmentRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Test
    @Transactional
    void testSaveShipment() {
        BeerOrder beerOrder = beerOrderRepository.save(BeerOrder.builder().build());

        BeerOrderShipment shipment = BeerOrderShipment.builder()
                .shipmentDate(LocalDateTime.now())
                .carrier("FedEx")
                .trackingNumber("12345")
                .beerOrder(beerOrder)
                .build();

        BeerOrderShipment savedShipment = shipmentRepository.save(shipment);

        assertThat(savedShipment).isNotNull();
        assertThat(savedShipment.getId()).isNotNull();
        assertThat(savedShipment.getBeerOrder().getId()).isEqualTo(beerOrder.getId());
    }
}
