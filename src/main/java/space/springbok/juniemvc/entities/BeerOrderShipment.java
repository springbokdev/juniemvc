package space.springbok.juniemvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entity representing a shipment of a beer order.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderShipment extends BaseEntity {

    @Builder
    public BeerOrderShipment(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate,
                             LocalDateTime shipmentDate, String carrier, String trackingNumber, BeerOrder beerOrder) {
        super(id, version, createdDate, updateDate);
        this.shipmentDate = shipmentDate;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.beerOrder = beerOrder;
    }

    @NotNull
    private LocalDateTime shipmentDate;

    private String carrier;

    private String trackingNumber;

    @ManyToOne
    private BeerOrder beerOrder;
}
