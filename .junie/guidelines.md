# Project Guidelines - juniemvc

This document provides essential information for developers working on the `juniemvc` project.

## Tech Stack
- **Java Version:** 21
- **Framework:** Spring Boot 3.5.6
- **Build Tool:** Maven
- **Persistence:** Spring Data JPA with H2 (Runtime)
- **Utilities:** Lombok (1.18.30), MapStruct (1.6.3)
- **Testing:** Spring Boot Starter Test (JUnit 5, Mockito)

## Project Structure
The project follows a standard Spring Boot package structure:
- `space.springbok.juniemvc.controllers`: REST Controllers for API endpoints.
- `space.springbok.juniemvc.entities`: JPA Entities for database mapping.
- `space.springbok.juniemvc.repositories`: Spring Data JPA repositories.
- `space.springbok.juniemvc.services`: Service interfaces and implementations (e.g., `BeerServiceJPA`).
- `src/main/resources`: Configuration files (`application.yaml`).
- `src/test/java`: Unit and integration tests.

## Running the Application
To run the application locally using Maven:
```bash
./mvnw spring-boot:run
```

## Testing
- **Run all tests:**
  ```bash
  ./mvnw test
  ```
- **Run a specific test class:**
  ```bash
  ./mvnw test -Dtest=BeerControllerTest
  ```

## Scripts and Build
- **Clean and Build:**
  ```bash
  ./mvnw clean install
  ```

## Best Practices & Coding Standards
- **Lombok:** Use `@Data`, `@Builder`, and `@RequiredArgsConstructor` to reduce boilerplate.
- **MapStruct:** 
  - Mappers are configured as Spring beans (`componentModel = "spring"`).
  - Use `lombok-mapstruct-binding` to ensure compatibility between Lombok and MapStruct.
- **REST APIs:**
  - Follow RESTful conventions.
  - Return `201 Created` for successful POST requests and `204 No Content` for successful DELETE requests.
  - Throw `NotFoundException` (mapped to 404) when resources are missing.
- **Testing:**
  - Controllers should be tested using `@WebMvcTest` and `MockMvc`.
  - Services should be tested with JUnit and Mockito if applicable, or as integration tests with `@SpringBootTest`.
- **Java 21 Features:** Utilize modern Java features like records, switch expressions, and sequenced collections where appropriate.
