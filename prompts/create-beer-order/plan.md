# Implementation Plan: Beer Order System

This plan outlines the steps to implement the Beer Order system in the `juniemvc` project, following the requirements in `prompts/create-beer-order/requirements.md` and adhering to the project's Spring Boot guidelines.

## Phase 1: Domain Model Refactoring

1.  **Introduce `BaseEntity`**
    *   Create `space.springbok.juniemvc.entities.BaseEntity` as a `@MappedSuperclass`.
    *   Include common fields: `id` (`@Id`, `@GeneratedValue`), `version` (`@Version`), `createdDate` (`@CreationTimestamp`), and `updateDate` (`@UpdateTimestamp`).
    *   Use Lombok `@Getter` and `@Setter`.

2.  **Refactor `Beer` Entity**
    *   Modify `space.springbok.juniemvc.entities.Beer` to extend `BaseEntity`.
    *   Remove redundant fields (`id`, `version`, `createdDate`, `updateDate`).
    *   Keep existing domain-specific fields (`beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`).

3.  **Implement `BeerOrder` Entity**
    *   Create `space.springbok.juniemvc.entities.BeerOrder` extending `BaseEntity`.
    *   Add fields: `customerRef`, `paymentAmount`, `status`.
    *   Define `@OneToMany` relationship with `BeerOrderLine` (cascading and orphan removal).
    *   Implement defensive synchronization method `addOrderLine(BeerOrderLine line)`.

4.  **Implement `BeerOrderLine` Entity**
    *   Create `space.springbok.juniemvc.entities.BeerOrderLine` extending `BaseEntity`.
    *   Define `@ManyToOne` relationships with `BeerOrder` and `Beer` (use `FetchType.LAZY`).
    *   Add fields: `orderQuantity`, `quantityAllocated`.

5.  **Create Repositories**
    *   Create `BeerOrderRepository` and `BeerOrderLineRepository` in `space.springbok.juniemvc.repositories`.

## Phase 2: DTOs and Mappers

1.  **Create DTO Records**
    *   Create `BeerOrderDto` and `BeerOrderLineDto` in `space.springbok.juniemvc.models`.
    *   Ensure they include all necessary fields from entities and `BaseEntity`.
    *   `BeerOrderLineDto` should include `beerId`.
    *   Apply Jakarta Validation (e.g., `@NotNull`, `@NotBlank`).

2.  **Implement Mappers**
    *   Create `BeerOrderMapper` and `BeerOrderLineMapper` in `space.springbok.juniemvc.mappers`.
    *   Configure with `@Mapper(componentModel = "spring")`.
    *   Ensure nested mapping between `BeerOrder` and `BeerOrderDto`.
    *   Handle `beer.id` to `beerId` mapping in `BeerOrderLineMapper`.

## Phase 3: Service Layer

1.  **Define `BeerOrderService` Interface**
    *   Add standard CRUD operations: `listOrders`, `getOrderById`, `saveNewOrder`, `updateOrder`, `deleteOrderById`.

2.  **Implement `BeerOrderServiceJPA`**
    *   Inject `BeerOrderRepository` and `BeerOrderMapper`.
    *   Implement CRUD logic using repository and mappers.
    *   Follow `@Transactional` guidelines (read-only for queries, standard for modifications).

## Phase 4: Controller Layer

1.  **Implement `BeerOrderController`**
    *   Create in `space.springbok.juniemvc.controllers` with package-private visibility.
    *   Map to `/api/v1/beer-order`.
    *   Implement REST endpoints for all CRUD operations.
    *   Use `@Valid` for input validation and return appropriate `ResponseEntity` or `@ResponseStatus`.

## Phase 5: Verification and Testing

1.  **Integration Testing**
    *   Create `BeerOrderRepositoryTest` to verify JPA mappings.
    *   Create `BeerOrderServiceJPATest` to verify business logic.

2.  **Controller Testing**
    *   Create `BeerOrderControllerTest` using `MockMvc` and `@WebMvcTest`.

3.  **Final Build**
    *   Run `./mvnw clean install` to ensure all tests pass and the project builds successfully.
