package space.springbok.juniemvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
 * DTO for BeerOrderShipment operations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BeerOrderShipmentDto extends BaseEntityDto {

    @NotNull(message = "Shipment date is required")
    private LocalDateTime shipmentDate;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    private Integer beerOrderId;
}
