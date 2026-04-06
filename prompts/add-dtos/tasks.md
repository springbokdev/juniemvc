# Task List: Refactor Beer Management to use DTOs

Based on the implementation plan in `prompts/add-dtos/plan.md`.

## Phase 1: Models and Mappers
1. [x] Create `BeerDto` record in `space.springbok.juniemvc.models`.
    - [x] Include all properties: `id`, `version`, `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`, `createdDate`, `updateDate`.
    - [x] Annotate with Lombok `@Builder`.
    - [x] Add Jakarta Validation annotations:
        - [x] `beerName`: `@NotBlank`, `@NotNull`
        - [x] `beerStyle`: `@NotNull`
        - [x] `price`: `@NotNull`
        - [x] `upc`: `@NotBlank`, `@NotNull`
2. [x] Create `BeerMapper` interface in `space.springbok.juniemvc.mappers`.
    - [x] Annotate with `@Mapper(componentModel = "spring")`.
    - [x] Define `beerDtoToBeer(BeerDto dto)` and ignore `id`, `createdDate`, and `updateDate`.
    - [x] Define `beerToBeerDto(Beer entity)`.

## Phase 2: Service Layer Refactoring
3. [x] Update `BeerService` interface method signatures to use `BeerDto`.
4. [x] Refactor `BeerServiceJPA` implementation:
    - [x] Inject `BeerMapper` via constructor.
    - [x] Update `listBeers()` to return `List<BeerDto>`.
    - [x] Update `getBeerById()` to return `Optional<BeerDto>`.
    - [x] Update `saveNewBeer()` to accept and return `BeerDto`.
    - [x] Update `updateBeerById()` to accept `BeerDto` and return `Optional<BeerDto>`.
    - [x] Ensure `deleteBeerById()` remains consistent with refactored logic.

## Phase 3: Controller Layer Refactoring
5. [x] Update `BeerController` methods:
    - [x] Use `BeerDto` for all request bodies and return types.
    - [x] Add `@Valid` to `@RequestBody` in `saveNewBeer` and `updateBeerById`.
    - [x] Ensure correct HTTP status codes are maintained.

## Phase 4: Verification and Testing
6. [x] Update `BeerControllerTest` to mock and expect `BeerDto`.
7. [x] Update `BeerServiceJPATest` to assert against `BeerDto`.
8. [x] Verify `BeerRepositoryTest` for persistence layer stability.
9. [x] Run final build and verification: `./mvnw clean install`.
