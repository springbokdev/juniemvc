package space.springbok.juniemvc.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer extends BaseEntity {

    @Builder
    public Beer(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate, String beerName, String beerStyle, String upc, Integer quantityOnHand, BigDecimal price) {
        super(id, version, createdDate, updateDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
    }

    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
}
