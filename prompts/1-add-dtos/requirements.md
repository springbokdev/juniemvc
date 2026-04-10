# Requirements: Refactor Beer Management to use DTOs

Refactor the `BeerController` and `BeerService` to separate the web layer from the persistence layer by introducing DTOs and MapStruct mappers, following the project's established guidelines.

## 1. Create BeerDto
- Create a new record `BeerDto` in the package `space.springbok.juniemvc.models`.
- Include the same properties as the `Beer` JPA entity: `id`, `version`, `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`, `createdDate`, and `updateDate`.
- Use Lombok's `@Builder` annotation for the DTO.
- Apply Jakarta Validation annotations on `BeerDto` properties to ensure data integrity:
    - `beerName`: Not blank, not null.
    - `beerStyle`: Not null.
    - `price`: Not null.
    - `upc`: Not blank, not null.

## 2. Create BeerMapper
- Create a MapStruct mapper interface `BeerMapper` in the package `space.springbok.juniemvc.mappers`.
- Configure the mapper with `componentModel = "spring"`.
- Define methods to convert:
    - `Beer` entity to `BeerDto`.
    - `BeerDto` to `Beer` entity.
- In the conversion from `BeerDto` to `Beer` entity, ignore the following properties: `id`, `createdDate`, and `updateDate`.

## 3. Refactor BeerService and BeerServiceJPA
- Update the `BeerService` interface and its implementation `BeerServiceJPA` to work with `BeerDto` instead of `Beer` entities.
- Inject `BeerMapper` into `BeerServiceJPA` using constructor injection.
- Use `BeerMapper` within `BeerServiceJPA` to handle conversions between the persistence layer (entities) and the service layer (DTOs).
- Ensure all service methods return or accept `BeerDto` objects.

## 4. Update BeerController
- Modify `BeerController` to use `BeerDto` for all request bodies and response types.
- Ensure the controller continues to follow RESTful principles and project guidelines:
    - Use constructor injection for dependencies.
    - Use `@Valid` to trigger validation on `BeerDto` in POST and PUT requests.
    - Return appropriate HTTP status codes (e.g., `201 Created` for saves, `204 No Content` for deletes).

## 5. Verification
- Update existing tests in `BeerControllerTest` and `BeerServiceJPATest` to reflect the change to DTOs.
- Ensure the application builds successfully and all tests pass: `./mvnw clean install`.
