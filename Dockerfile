FROM ubuntu:latest AS build

RUN apt-get update

RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN chmod +x mvnw

RUN ./mvnw spring-boot:run

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/AtmEndPoints.jar AtmApplication.jar



ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["java", "-jar", "app.jar"]

# Use a base image with Java and Maven pre-installed
FROM openjdk:17-jdk-slim AS build

# Copy the source code into the container
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a lightweight base image for running the application
FROM adoptopenjdk/openjdk11:alpine-jre

# Copy the JAR file from the build stage into the new container
# COPY --from=build /app/target/AtmEndPoints.jar AtmApplication.jar
COPY --from=build /build/libs/AtmEndPoints.jar AtmApplication.jar

# Expose the port that the application will listen on
EXPOSE 8080

# Set an environment variable to customize the application's behavior
ENV SPRING_PROFILES_ACTIVE=production

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]