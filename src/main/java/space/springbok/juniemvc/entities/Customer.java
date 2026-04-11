package space.springbok.juniemvc.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Builder
    public Customer(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate,
                    String name, String email, String phoneNumber, String addressLine1, String addressLine2,
                    String city, String state, String postalCode, Set<BeerOrder> beerOrders) {
        super(id, version, createdDate, updateDate);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.beerOrders = beerOrders == null ? new HashSet<>() : beerOrders;
    }

    @NotNull
    @Column(nullable = false)
    private String name;

    private String email;

    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false)
    private String state;

    @NotNull
    @Column(nullable = false)
    private String postalCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BeerOrder> beerOrders = new HashSet<>();

    public void addBeerOrder(BeerOrder beerOrder) {
        if (this.beerOrders == null) {
            this.beerOrders = new HashSet<>();
        }
        this.beerOrders.add(beerOrder);
        beerOrder.setCustomer(this);
    }
}
