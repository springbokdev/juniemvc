package space.springbok.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.springbok.juniemvc.entities.Customer;
import space.springbok.juniemvc.models.CustomerDto;

/**
 * Mapper for Customer operations.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Map a CustomerDto to a Customer entity, ignoring ID and timestamps.
     *
     * @param dto the CustomerDto to map.
     * @return the Customer entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrders", ignore = true)
    Customer customerDtoToCustomer(CustomerDto dto);

    /**
     * Map a Customer entity to a CustomerDto.
     *
     * @param entity the Customer entity to map.
     * @return the CustomerDto.
     */
    CustomerDto customerToCustomerDto(Customer entity);
}
