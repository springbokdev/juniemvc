package space.springbok.juniemvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrder extends BaseEntity {

    @Builder
    public BeerOrder(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate, Customer customer, BigDecimal paymentAmount, String status, Set<BeerOrderLine> beerOrderLines) {
        super(id, version, createdDate, updateDate);
        this.customer = customer;
        this.paymentAmount = paymentAmount;
        this.status = status;
        this.beerOrderLines = beerOrderLines == null ? new HashSet<>() : beerOrderLines;
    }

    @ManyToOne
    private Customer customer;
    private BigDecimal paymentAmount;
    private String status;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

    public void addOrderLine(BeerOrderLine line) {
        if (this.beerOrderLines == null) {
            this.beerOrderLines = new HashSet<>();
        }
        this.beerOrderLines.add(line);
        line.setBeerOrder(this);
    }
}
