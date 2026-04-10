Based on the provided ERD, here are the detailed instructions for a Java developer to implement the relationships between `BeerOrder`, `BeerOrderLine`, and `Beer` using JPA, Hibernate, and Lombok.

#### 1. Entity: `BeerOrder`
This represents the header/parent entity for a customer order.

*   **Relationship:** One-to-Many with `BeerOrderLine`.
*   **Lombok Annotations:** `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.
*   **JPA Mapping:**
    *   Use `@OneToMany` with `mappedBy = "beerOrder"` to define the inverse side of the relationship.
    *   Apply `cascade = CascadeType.ALL` and `orphanRemoval = true` to ensure that order lines are managed through the lifecycle of the order.
    *   Initialize the collection to avoid null pointers: `private Set<BeerOrderLine> beerOrderLines = new HashSet<>();`.

#### 2. Entity: `BeerOrderLine`
This is the join entity that links a specific order to the products (beers) it contains.

*   **Relationships:**
    *   Many-to-One with `BeerOrder`.
    *   Many-to-One with `Beer`.
*   **Lombok Annotations:** `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.
*   **JPA Mapping:**
    *   **BeerOrder Reference:** Use `@ManyToOne` annotation. This is the **owning side** of the relationship with `BeerOrder`.
    *   **Beer Reference:** Use `@ManyToOne` annotation. This links the line item to the product catalog.
    *   **Fetch Strategy:** It is highly recommended to use `FetchType.LAZY` for these associations to prevent unnecessary database hits.

#### 3. Entity: `Beer`
The catalog entity representing the products available for order.

*   **Relationship:** One-to-Many with `BeerOrderLine`.
*   **Lombok Annotations:** `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.
*   **JPA Mapping:**
    *   Use `@OneToMany` with `mappedBy = "beer"` to allow navigation from a beer to all its order occurrences.

---

### Implementation Snippet (Conceptual)

```java
// BeerOrder.java
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BeerOrderLine> beerOrderLines;
    
    private String customerRef;
    private BigDecimal paymentAmount;
    private String status;
}

// BeerOrderLine.java
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BeerOrder beerOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Beer beer;

    private Integer orderQuantity;
    private Integer quantityAllocated;
}

// Beer.java
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;
    
    private String beerName;
    private BigDecimal price;
}
```

### Best Practices & Notes
1.  **Avoid `@Data`:** Do not use Lombok's `@Data` on JPA entities. It generates `hashCode` and `equals` methods that include collection fields, which can lead to `StackOverflowError` during lazy loading or infinite recursion in bi-directional relationships.
2.  **Audit Fields:** For `version`, `createdDate`, and `updateDate` (shown in the ERD), use Hibernate's `@Version`, `@CreationTimestamp`, and `@UpdateTimestamp` annotations to automate their management.
3.  **Defensive Synchronization:** In `BeerOrder`, implement a helper method to keep both sides of the bi-directional relationship in sync:
    ```java
    public void addOrderLine(BeerOrderLine line) {
        if (this.beerOrderLines == null) {
            this.beerOrderLines = new HashSet<>();
        }
        this.beerOrderLines.add(line);
        line.setBeerOrder(this);
    }
    ```
4.  **Equality:** Implement `equals` and `hashCode` manually using only the primary key (`id`) or a natural business key to ensure consistent behavior within Hibernate Sessions.