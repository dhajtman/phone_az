# Use an official OpenJDK runtime as a parent image
#FROM openjdk:17-jdk-alpine
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/*.jar /app/telekom-spring-boot-docker.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/telekom-spring-boot-docker.jar"]