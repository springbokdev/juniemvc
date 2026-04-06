package space.springbok.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.models.BeerDto;

/**
 * Mapper for Beer operations.
 */
@Mapper(componentModel = "spring")
public interface BeerMapper {

    /**
     * Map a BeerDto to a Beer entity, ignoring ID and timestamps.
     *
     * @param dto the BeerDto to map.
     * @return the Beer entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Beer beerDtoToBeer(BeerDto dto);

    /**
     * Map a Beer entity to a BeerDto.
     *
     * @param entity the Beer entity to map.
     * @return the BeerDto.
     */
    BeerDto beerToBeerDto(Beer entity);
}
