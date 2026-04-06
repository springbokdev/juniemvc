# Requirements: Beer Order System Implementation

## Overview
Implement a robust ordering system that allows customers to place orders for various beers. This system will extend the existing beer management functionality by introducing `BeerOrder` and `BeerOrderLine` entities, along with their respective DTOs, mappers, services, and REST controllers.

## 1. Domain Model Refactoring

### 1.1 `BaseEntity` (New)
To reduce code duplication and maintain consistency across all JPA entities, introduce a `BaseEntity` class.
- **Location:** `space.springbok.juniemvc.entities.BaseEntity`
- **Annotations:** `@MappedSuperclass`, `@Getter`, `@Setter`.
- **Properties:**
    - `id`: `Integer`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
    - `version`: `Integer`, `@Version`.
    - `createdDate`: `LocalDateTime`, `@CreationTimestamp`, `@Column(updatable = false)`.
    - `updateDate`: `LocalDateTime`, `@UpdateTimestamp`.

### 1.2 `Beer` (Refactor)
- Refactor the existing `Beer` entity to extend `BaseEntity`.
- Remove `id`, `version`, `createdDate`, and `updateDate` fields as they are now inherited.
- Maintain existing fields: `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`.

### 1.3 `BeerOrder` (New)
Represents the header of a customer order.
- **Location:** `space.springbok.juniemvc.entities.BeerOrder`
- **Extends:** `BaseEntity`.
- **Properties:**
    - `customerRef`: `String` (optional).
    - `paymentAmount`: `BigDecimal`.
    - `status`: `String` (consider using an Enum if preferred).
    - `beerOrderLines`: `Set<BeerOrderLine>`, `@OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true)`.
- **Lombok:** `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.

### 1.4 `BeerOrderLine` (New)
The join entity linking an order to specific beers and quantities.
- **Location:** `space.springbok.juniemvc.entities.BeerOrderLine`
- **Extends:** `BaseEntity`.
- **Properties:**
    - `beerOrder`: `BeerOrder`, `@ManyToOne(fetch = FetchType.LAZY)`.
    - `beer`: `Beer`, `@ManyToOne(fetch = FetchType.LAZY)`.
    - `orderQuantity`: `Integer`.
    - `quantityAllocated`: `Integer`.
- **Lombok:** `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.

## 2. Data Transfer Objects (DTOs)

Create Java records for the new entities in the `space.springbok.juniemvc.models` package.

### 2.1 `BeerOrderDto`
- Include all fields from `BeerOrder` and `BaseEntity`.
- Include a list/set of `BeerOrderLineDto`.
- Use Jakarta Validation annotations (e.g., `@NotNull` for `paymentAmount`).

### 2.2 `BeerOrderLineDto`
- Include all fields from `BeerOrderLine` and `BaseEntity`.
- Include `beerId` (Integer) to reference the beer.

## 3. Mappers (MapStruct)

Create mappers in the `space.springbok.juniemvc.mappers` package.

### 3.1 `BeerOrderMapper`
- Map between `BeerOrder` and `BeerOrderDto`.
- Ensure nested mapping for `BeerOrderLine` is handled correctly.

### 3.2 `BeerOrderLineMapper`
- Map between `BeerOrderLine` and `BeerOrderLineDto`.
- Map `beer.id` to `beerId` in the DTO.

## 4. Service Layer

### 4.1 `BeerOrderService` (New)
- Define an interface for managing beer orders.
- Provide methods for:
    - `listOrders()`: Returns a list of `BeerOrderDto`.
    - `getOrderById(Integer id)`: Returns an `Optional<BeerOrderDto>`.
    - `saveNewOrder(BeerOrderDto order)`: Saves a new order and returns the saved DTO.
    - `updateOrder(Integer id, BeerOrderDto order)`: Updates an existing order.
    - `deleteOrderById(Integer id)`: Deletes an order.

### 4.2 `BeerOrderServiceJPA` (Implementation)
- Implement `BeerOrderService` using JPA repositories and MapStruct mappers.
- Follow the project's transaction management guidelines (`@Transactional`).

## 5. Controller Layer

### 5.1 `BeerOrderController` (New)
- **Location:** `space.springbok.juniemvc.controllers.BeerOrderController`
- **Endpoint:** `/api/v1/beer-order`
- **Visibility:** Package-private as per Spring Boot Guidelines.
- **Methods:**
    - `GET /`: List all orders.
    - `GET /{orderId}`: Get order by ID.
    - `POST /`: Create a new order (with `@Valid`).
    - `PUT /{orderId}`: Update an order.
    - `DELETE /{orderId}`: Delete an order.

## 6. Verification and Testing

- **Integration Tests:** Use Testcontainers to verify the JPA relationships and service layer logic.
- **Controller Tests:** Use `MockMvc` and `@WebMvcTest` to verify the REST API behavior and validation rules.
- **Checklist:**
    - [ ] `BaseEntity` correctly implemented and inherited.
    - [ ] `BeerOrder` and `BeerOrderLine` relationships correctly mapped (Lazy fetch, cascading).
    - [ ] DTOs and Mappers correctly handle nested collections.
    - [ ] All API endpoints functional and return correct HTTP status codes.
    - [ ] All tests passing with `./mvnw clean install`.
