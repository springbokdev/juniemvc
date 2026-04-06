package space.springbok.juniemvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLine extends BaseEntity {

    @Builder
    public BeerOrderLine(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate, BeerOrder beerOrder, Beer beer, Integer orderQuantity, Integer quantityAllocated) {
        super(id, version, createdDate, updateDate);
        this.beerOrder = beerOrder;
        this.beer = beer;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private BeerOrder beerOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Beer beer;

    private Integer orderQuantity;
    private Integer quantityAllocated;
}
