# Implementation Plan for Customer Entity and Controller

This plan outlines the steps required to implement the `Customer` entity, its corresponding RESTful API, and related components, following the specifications in `prompts/7-add-customer-entity-and-controller/requirements.md` and adhering to the project's Spring Boot guidelines.

## 1. Persistence Layer

- **Task 1.1: Create Customer Entity**
  - Create `space.springbok.juniemvc.entities.Customer` extending `BaseEntity`.
  - Add properties: `name`, `email`, `phoneNumber`, `addressLine1`, `addressLine2`, `city`, `state`, `postalCode`.
  - Define `@OneToMany` relationship with `BeerOrder`.
  - Update `BeerOrder` entity:
    - Replace `customerRef` (String) with `@ManyToOne` relationship to `Customer` entity.
    - Update `@Builder` and existing constructors to handle the new `Customer` relationship.
  - Use Lombok `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, and a custom `@Builder` for the entity.

- **Task 1.2: Create Customer Repository**
  - Create `space.springbok.juniemvc.repositories.CustomerRepository` extending `JpaRepository<Customer, Integer>`.

- **Task 1.3: Database Migration**
  - Create a new Flyway migration script `src/main/resources/db/migration/V2__add_customer_table.sql`.
  - Script should:
    - Create `customer` table.
    - Alter `beer_order` table: add `customer_id` column as a foreign key to `customer.id`, and remove or repurpose `customer_ref`.

## 2. Business Logic Layer

- **Task 2.1: Create Customer DTO**
  - Create `space.springbok.juniemvc.models.CustomerDto` extending `BaseEntityDto`.
  - Add Jakarta Validation annotations (`@NotBlank`, `@Email`).
  - Use Lombok `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@SuperBuilder`, and `@EqualsAndHashCode(callSuper = true)`.

- **Task 2.2: Create Customer Mapper**
  - Create `space.springbok.juniemvc.mappers.CustomerMapper` using MapStruct for mapping between `Customer` and `CustomerDto`.

- **Task 2.3: Create Customer Service**
  - Define `space.springbok.juniemvc.services.CustomerService` interface with CRUD operations.
  - Implement `space.springbok.juniemvc.services.CustomerServiceJPA` using `CustomerRepository` and `CustomerMapper`.
  - Ensure transactional boundaries: `@Transactional(readOnly = true)` for reads, `@Transactional` for writes.
  - Use constructor injection.

## 3. Web Layer

- **Task 3.1: Create Customer Controller**
  - Create `space.springbok.juniemvc.controllers.CustomerController`.
  - Map to `/api/v1/customer`.
  - Implement GET (list and by ID), POST, PUT, and DELETE endpoints.
  - Use package-private visibility.
  - Use constructor injection for `CustomerService`.
  - Return appropriate HTTP status codes and `ResponseEntity<T>` where necessary.

## 4. Documentation

- **Task 4.1: Update OpenAPI Specification**
  - Create path definitions: `openapi/openapi/paths/api_v1_customer.yaml` and `openapi/openapi/paths/api_v1_customer_{customerId}.yaml`.
  - Create schema definition: `openapi/openapi/components/schemas/Customer.yaml`.
  - Reference new paths and schema in `openapi/openapi/openapi.yaml`.
  - Validate the specification using `npm test` in the `openapi/` directory.

## 5. Testing

- **Task 5.1: Persistence Layer Testing**
  - Create `CustomerRepositoryTest` using `@DataJpaTest` to verify JPA mappings and repository operations.

- **Task 5.2: Service Layer Testing**
  - Create `CustomerServiceJPATest` to test business logic and service-layer interactions.

- **Task 5.3: Web Layer Testing**
  - Create `CustomerControllerTest` using `MockMvc` to test REST endpoints and validation.

- **Task 5.4: Integration Testing**
  - Create an integration test (e.g., `CustomerControllerIT`) using `@SpringBootTest` with a random port to verify end-to-end functionality.

## 6. Verification and Cleanup

- **Task 6.1: Final Build and Check**
  - Run all tests to ensure they are passing.
  - Verify adherence to project guidelines (package-private visibility, constructor injection, transaction management).
  - Check for any N+1 query issues if associations are fetched (following OSIV-disabled guideline).
