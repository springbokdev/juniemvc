# Task List: Beer Order System Implementation

This task list tracks the implementation of the Beer Order system in the `juniemvc` project, based on the plan in `prompts/create-beer-order/plan.md`.

## Phase 1: Domain Model Refactoring
1. [x] **Introduce `BaseEntity`**
    - [x] Create `space.springbok.juniemvc.entities.BaseEntity` as a `@MappedSuperclass`.
    - [x] Add fields: `id` (`Integer`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`).
    - [x] Add fields: `version` (`Integer`, `@Version`).
    - [x] Add fields: `createdDate` (`LocalDateTime`, `@CreationTimestamp`, `@Column(updatable = false)`).
    - [x] Add fields: `updateDate` (`LocalDateTime`, `@UpdateTimestamp`).
    - [x] Annotate with Lombok `@Getter` and `@Setter`.

2. [x] **Refactor `Beer` Entity**
    - [x] Modify `space.springbok.juniemvc.entities.Beer` to extend `BaseEntity`.
    - [x] Remove redundant fields: `id`, `version`, `createdDate`, `updateDate`.
    - [x] Ensure existing fields (`beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`) remain.

3. [x] **Implement `BeerOrder` Entity**
    - [x] Create `space.springbok.juniemvc.entities.BeerOrder` extending `BaseEntity`.
    - [x] Add fields: `customerRef` (`String`), `paymentAmount` (`BigDecimal`), `status` (`String`).
    - [x] Define `@OneToMany` relationship with `BeerOrderLine` (`mappedBy = "beerOrder"`, `cascade = CascadeType.ALL`, `orphanRemoval = true`).
    - [x] Initialize `beerOrderLines` set: `private Set<BeerOrderLine> beerOrderLines = new HashSet<>();`.
    - [x] Implement defensive synchronization method `addOrderLine(BeerOrderLine line)`.
    - [x] Annotate with Lombok `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Getter`, `@Setter`.

4. [x] **Implement `BeerOrderLine` Entity**
    - [x] Create `space.springbok.juniemvc.entities.BeerOrderLine` extending `BaseEntity`.
    - [x] Define `@ManyToOne` relationship with `BeerOrder` (`fetch = FetchType.LAZY`).
    - [x] Define `@ManyToOne` relationship with `Beer` (`fetch = FetchType.LAZY`).
    - [x] Add fields: `orderQuantity` (`Integer`), `quantityAllocated` (`Integer`).
    - [x] Annotate with Lombok `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Getter`, `@Setter`.

5. [x] **Create Repositories**
    - [x] Create `space.springbok.juniemvc.repositories.BeerOrderRepository` extending `JpaRepository`.
    - [x] Create `space.springbok.juniemvc.repositories.BeerOrderLineRepository` extending `JpaRepository`.

## Phase 2: DTOs and Mappers

6. [x] **Create DTO Records**
    - [x] Create `space.springbok.juniemvc.models.BeerOrderDto` (record) including all fields and nested `BeerOrderLineDto` set.
    - [x] Create `space.springbok.juniemvc.models.BeerOrderLineDto` (record) including all fields and `beerId` (Integer).
    - [x] Apply Jakarta Validation annotations:
        - [x] `BeerOrderDto`: `@NotNull` for `paymentAmount`, etc.
        - [x] `BeerOrderLineDto`: `@NotNull` for `beerId`, `@NotNull` for `orderQuantity`.
    - [x] Annotate with Lombok `@Builder`.

7. [x] **Implement Mappers**
    - [x] Create `space.springbok.juniemvc.mappers.BeerOrderMapper` (interface) annotated with `@Mapper(componentModel = "spring")`.
    - [x] Create `space.springbok.juniemvc.mappers.BeerOrderLineMapper` (interface) annotated with `@Mapper(componentModel = "spring")`.
    - [x] Configure `BeerOrderLineMapper` to map `beer.id` to `beerId` in the DTO.

## Phase 3: Service Layer

8. [x] **Define `BeerOrderService` Interface**
    - [x] Define interface `space.springbok.juniemvc.services.BeerOrderService`.
    - [x] Add methods: `listOrders()`, `getOrderById(Integer id)`, `saveNewOrder(BeerOrderDto order)`, `updateOrder(Integer id, BeerOrderDto order)`, `deleteOrderById(Integer id)`.

9. [x] **Implement `BeerOrderServiceJPA`**
    - [x] Create implementation `space.springbok.juniemvc.services.BeerOrderServiceJPA`.
    - [x] Inject `BeerOrderRepository` and `BeerOrderMapper` via constructor.
    - [x] Implement CRUD logic with `@Transactional` (read-only where applicable).

## Phase 4: Controller Layer

10. [x] **Implement `BeerOrderController`**
    - [x] Create `space.springbok.juniemvc.controllers.BeerOrderController` with package-private visibility.
    - [x] Map to `/api/v1/beer-order`.
    - [x] Implement REST endpoints for CRUD operations using `BeerOrderService`.
    - [x] Use `@Valid` for request body validation.

## Phase 5: Verification and Testing

11. [x] **Integration Testing**
    - [x] Create `space.springbok.juniemvc.repositories.BeerOrderRepositoryTest`.
    - [x] Create `space.springbok.juniemvc.services.BeerOrderServiceJPATest`.

12. [x] **Controller Testing**
    - [x] Create `space.springbok.juniemvc.controllers.BeerOrderControllerTest` using `MockMvc`.

13. [x] **Final Build**
    - [x] Run `./mvnw clean install` and verify all tests pass.
