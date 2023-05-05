# Use a base image with Java and Maven pre-installed
FROM openjdk:17-jdk-slim AS build

VOLUME /tmp

# Copy the source code into the container
COPY target/*.jar app.jar

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expose the port that the application will listen on
EXPOSE 8080