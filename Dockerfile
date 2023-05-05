# Use a base image with Java and Maven pre-installed
FROM maven:3.0.2-jdk-17-slim AS build

# Copy the source code into the container
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /target/atm-0.0.1-SNAPSHOT.jar atm.jar

# Expose the port that the application will listen on
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "atm.jar"]
