package space.springbok.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import space.springbok.juniemvc.entities.BeerOrderShipment;
import space.springbok.juniemvc.models.BeerOrderShipmentDto;

/**
 * Mapper for BeerOrderShipment operations.
 */
@Mapper(componentModel = "spring")
public interface BeerOrderShipmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrder.id", source = "beerOrderId")
    BeerOrderShipment shipmentDtoToShipment(BeerOrderShipmentDto dto);

    @Mapping(target = "beerOrderId", source = "beerOrder.id")
    BeerOrderShipmentDto shipmentToShipmentDto(BeerOrderShipment entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrder.id", source = "beerOrderId")
    BeerOrderShipment updateShipmentFromDto(BeerOrderShipmentDto dto, @MappingTarget BeerOrderShipment entity);
}
