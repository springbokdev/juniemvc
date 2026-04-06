package space.springbok.juniemvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Beer operations.
 */
@Builder
public record BeerDto(
        Integer id,
        Integer version,

        @NotBlank
        @NotNull
        String beerName,

        @NotNull
        String beerStyle,

        @NotBlank
        @NotNull
        String upc,

        Integer quantityOnHand,

        @NotNull
        BigDecimal price,

        LocalDateTime createdDate,
        LocalDateTime updateDate
) {
}
