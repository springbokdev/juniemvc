package space.springbok.juniemvc.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record BeerOrderDto(
        Integer id,
        Integer version,
        LocalDateTime createdDate,
        LocalDateTime updateDate,
        String customerRef,
        @NotNull BigDecimal paymentAmount,
        String status,
        Set<BeerOrderLineDto> beerOrderLines
) {
}
