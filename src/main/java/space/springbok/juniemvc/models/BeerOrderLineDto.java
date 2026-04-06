package space.springbok.juniemvc.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BeerOrderLineDto(
        Integer id,
        Integer version,
        LocalDateTime createdDate,
        LocalDateTime updateDate,
        Integer orderQuantity,
        Integer quantityAllocated,
        @NotNull Integer beerId
) {
}
