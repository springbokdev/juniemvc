package space.springbok.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.springbok.juniemvc.entities.BeerOrderLine;
import space.springbok.juniemvc.models.BeerOrderLineDto;

@Mapper
public interface BeerOrderLineMapper {

    @Mapping(target = "beerId", source = "beer.id")
    BeerOrderLineDto beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    @Mapping(target = "beer", ignore = true)
    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto);
}
