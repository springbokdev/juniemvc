package space.springbok.juniemvc.services;

import space.springbok.juniemvc.models.BeerOrderShipmentDto;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing BeerOrderShipment operations.
 */
public interface BeerOrderShipmentService {

    List<BeerOrderShipmentDto> listShipments();

    Optional<BeerOrderShipmentDto> getShipmentById(Integer id);

    BeerOrderShipmentDto saveNewShipment(BeerOrderShipmentDto shipmentDto);

    Optional<BeerOrderShipmentDto> updateShipment(Integer id, BeerOrderShipmentDto shipmentDto);

    Boolean deleteShipmentById(Integer id);
}
