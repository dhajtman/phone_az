# Phone Application

## Project Requirements

### Prerequisites
- Java 17
- Maven 3.6+
- Docker

### Case study
Create a Java application that returns a sorted list of phones using a REST interface. The phones in the list should be sorted in ascending order by name and the list should contain only unique items. Each phone should have an identifier and a name. The list of phones will be stored directly in memory. The task also includes updating the data from the primary source (which can be statically defined in a Java class) into memory. The list of phones should contain at least 3 items. The project should be covered by unit tests. (Preferred use of Spring Boot framework and Swagger interface)

### Building the Project
1. **Package the project using Maven:**
   ```bash
   mvn package
   ```
2. **Build the Docker image:**
   ```bash
   docker build -t telekom-spring-boot-docker .
   ```
3. **Run the Docker container:**
   ```bash
   docker run --name telekom-app-api -p 8080:8080 -t telekom-spring-boot-docker
   ```

### Running Tests
To run the tests, use the following Maven command:
```bash
mvn test
```

### Application Endpoints
- **GET /phone/all: Retrieve all phones**
- **POST /phone/update: Update phones from the primary source**

### Configuration
The application can be configured using the application.properties file located in src/main/resources:
```properties
spring.application.name=phone
```

### Application Access
Access application Swagger UI at: http://localhost:8080/swagger-ui/index.html