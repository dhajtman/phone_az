# Phone Application

## Project Requirements

### Prerequisites
- Java 17
- Maven 3.6+
- Docker

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