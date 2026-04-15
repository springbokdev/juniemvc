# Implementation Plan: Refactor Beer Management to use DTOs

This plan outlines the steps to refactor the beer management functionality in `juniemvc` to use DTOs, separating the web layer from the persistence layer.

## Phase 1: Models and Mappers

1.  **Create `BeerDto` Record**
    *   **Location:** `space.springbok.juniemvc.models.BeerDto`
    *   **Properties:** Same as `Beer` entity (id, version, beerName, beerStyle, upc, quantityOnHand, price, createdDate, updateDate).
    *   **Lombok:** Use `@Builder`.
    *   **Validation:**
        *   `beerName`: `@NotBlank`, `@NotNull`
        *   `beerStyle`: `@NotNull`
        *   `price`: `@NotNull`
        *   `upc`: `@NotBlank`, `@NotNull`

2.  **Create `BeerMapper` Interface**
    *   **Location:** `space.springbok.juniemvc.mappers.BeerMapper`
    *   **Configuration:** `@Mapper(componentModel = "spring")`
    *   **Methods:**
        *   `Beer beerDtoToBeer(BeerDto dto)`: Ignore `id`, `createdDate`, and `updateDate` using `@Mapping(target = "id", ignore = true)`, etc.
        *   `BeerDto beerToBeerDto(Beer entity)`

## Phase 2: Service Layer Refactoring

1.  **Update `BeerService` Interface**
    *   Change method signatures to accept and return `BeerDto` instead of `Beer`.

2.  **Refactor `BeerServiceJPA` Implementation**
    *   Inject `BeerMapper` via constructor.
    *   Update `listBeers()`: Map list of `Beer` entities to list of `BeerDto`.
    *   Update `getBeerById(Integer id)`: Find entity and map to `Optional<BeerDto>`.
    *   Update `saveNewBeer(BeerDto dto)`: Map `BeerDto` to entity, save, then map back to `BeerDto`.
    *   Update `updateBeerById(Integer id, BeerDto dto)`: Find entity, update fields from `BeerDto` (using mapper or manual setters), save, and map back to `Optional<BeerDto>`.
    *   `deleteBeerById(Integer id)`: Keep logic, but ensure it aligns with any service-wide changes.

## Phase 3: Controller Layer Refactoring

1.  **Update `BeerController`**
    *   Update all methods to use `BeerDto` for request bodies and return types.
    *   Add `@Valid` to `@RequestBody` parameters in `saveNewBeer` and `updateBeerById`.
    *   Ensure methods like `saveNewBeer` return `ResponseEntity` or continue using `@ResponseStatus(HttpStatus.CREATED)` as per current code, but return `BeerDto`.

## Phase 4: Verification and Testing

1.  **Update Tests**
    *   **`BeerControllerTest`**: Update mocks to return `BeerDto` and requests to send `BeerDto`.
    *   **`BeerServiceJPATest`**: Update assertions to work with `BeerDto`.
    *   **`BeerRepositoryTest`**: Ensure no regressions in the persistence layer.

2.  **Run Build and Tests**
    *   Execute `./mvnw clean install` to verify the entire project.
