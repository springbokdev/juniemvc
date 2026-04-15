# Requirements for Customer Entity and Controller

## Overview
Add a new `Customer` entity to the project to represent a customer who can place beer orders. Implement a full RESTful CRUD API for managing customers, including persistence, service, and web layers.

## 1. Persistence Layer

### 1.1 Customer Entity
Create a `Customer` JPA entity in the `space.springbok.juniemvc.entities` package.
- **Base Class:** Must extend `space.springbok.juniemvc.entities.BaseEntity`.
- **Properties:**
  - `name`: `String`, Not Null.
  - `email`: `String`.
  - `phoneNumber`: `String`.
  - `addressLine1`: `String`, Not Null.
  - `addressLine2`: `String`.
  - `city`: `String`, Not Null.
  - `state`: `String`, Not Null.
  - `postalCode`: `String`, Not Null.
- **Relationships:**
  - Define a `@OneToMany` relationship with `BeerOrder` (a customer has many orders).
  - Update `BeerOrder` entity to have a `@ManyToOne` relationship with `Customer`. Replace the current `customerRef` (String) with a reference to the `Customer` entity.
- **Lombok:** Use `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, and a custom `@Builder` that includes `super` fields (matching existing entity patterns).

### 1.2 Customer Repository
Create a `CustomerRepository` interface in the `space.springbok.juniemvc.repositories` package extending `JpaRepository<Customer, Integer>`.

### 1.3 Database Migration
Add a new Flyway migration script in `src/main/resources/db/migration/` (e.g., `V2__add_customer_table.sql`) to:
- Create the `customer` table with all specified fields and the primary key.
- Update the `beer_order` table to include a `customer_id` foreign key column and remove or repurpose `customer_ref` if necessary (follow project's migration style).

## 2. Business Logic Layer

### 2.1 Customer DTO
Create a `CustomerDto` class in the `space.springbok.juniemvc.models` package.
- **Base Class:** Must extend `space.springbok.juniemvc.models.BaseEntityDto`.
- **Validation:** Apply Jakarta Validation annotations:
  - `@NotBlank` for `name`, `addressLine1`, `city`, `state`, and `postalCode`.
  - `@Email` for `email` (optional but recommended).
- **Lombok:** Use `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@SuperBuilder`, and `@EqualsAndHashCode(callSuper = true)`.

### 2.2 Customer Mapper
Create a `CustomerMapper` interface in the `space.springbok.juniemvc.mappers` package using MapStruct.
- Define mapping methods between `Customer` entity and `CustomerDto`.

### 2.3 Customer Service
Create a `CustomerService` interface in the `space.springbok.juniemvc.services` package.
- Define CRUD operations: `listCustomers`, `getCustomerById`, `saveNewCustomer`, `updateCustomerById`, and `deleteCustomerById`.
- Implement this interface in `CustomerServiceJPA` using the `CustomerRepository`.
- Apply `@Service` and `@Transactional` as per Spring Boot guidelines. Use `readOnly = true` for query methods.

## 3. Web Layer

### 3.1 Customer Controller
Create a `CustomerController` class in the `space.springbok.juniemvc.controllers` package.
- **Base Path:** `/api/v1/customer`
- **Visibility:** Use package-private visibility for the class and its handler methods.
- **Injection:** Use constructor injection for `CustomerService`.
- **Endpoints:**
  - `GET /api/v1/customer`: List all customers.
  - `GET /api/v1/customer/{customerId}`: Get a customer by ID (return 404 if not found).
  - `POST /api/v1/customer`: Create a new customer (return 201 Created and the saved DTO).
  - `PUT /api/v1/customer/{customerId}`: Update an existing customer.
  - `DELETE /api/v1/customer/{customerId}`: Delete a customer (return 204 No Content).
- **Responses:** Use `ResponseEntity<T>` or `@ResponseStatus` where appropriate.

## 4. Documentation

### 4.1 OpenAPI Specification
Update the OpenAPI documentation in the `openapi/` directory following the existing modular structure:
- Create `openapi/openapi/paths/api_v1_customer.yaml` and `openapi/openapi/paths/api_v1_customer_{customerId}.yaml`.
- Create `openapi/openapi/components/schemas/Customer.yaml`.
- Reference these new files in `openapi/openapi/openapi.yaml`.
- Ensure all new API operations and models are correctly documented.
- Verify with `npm test` in the `openapi/` directory.

## 5. Testing

### 5.1 Unit and Integration Tests
- **Controller Tests:** Create `CustomerControllerTest` to test the REST endpoints using `MockMvc`. Mock the `CustomerService`.
- **Service Tests:** Create `CustomerServiceJPATest` to test the business logic.
- **Repository Tests:** Create `CustomerRepositoryTest` to verify data persistence (use `@DataJpaTest`).
- **Integration Tests:** Use `Testcontainers` (if applicable in project) or `@SpringBootTest` with a random port to verify the end-to-end flow.

## 6. Guidelines Adherence
- Use **Constructor Injection** for all dependencies.
- Use **Package-private** visibility for Spring components (Controllers, Services, etc.) where possible.
- Ensure **Transaction Boundaries** are clearly defined in the service layer.
- **Separate Web and Persistence Layers** using DTOs and Mappers.
- Follow **REST API Design Principles** as specified in the project guidelines.
