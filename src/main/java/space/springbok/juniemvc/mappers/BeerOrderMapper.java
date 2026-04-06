package space.springbok.juniemvc.mappers;

import org.mapstruct.Mapper;
import space.springbok.juniemvc.entities.BeerOrder;
import space.springbok.juniemvc.models.BeerOrderDto;

@Mapper(uses = BeerOrderLineMapper.class)
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto beerOrderDto);
}
