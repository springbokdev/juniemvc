package space.springbok.juniemvc.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.models.BeerPatchDto;

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

    /**
     * Update an existing Beer entity with values from BeerDto.
     *
     * @param dto the BeerDto to map.
     * @param entity the Beer entity to update.
     * @return the updated Beer entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Beer updateBeerFromDto(BeerDto dto, @MappingTarget Beer entity);

    /**
     * Patch an existing Beer entity with values from BeerPatchDto, ignoring nulls.
     *
     * @param dto    the BeerPatchDto to map.
     * @param entity the Beer entity to update.
     * @return the updated Beer entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Beer patchBeerFromDto(BeerPatchDto dto, @MappingTarget Beer entity);
}
