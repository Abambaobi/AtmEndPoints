# Use a base image with Java and Maven pre-installed
FROM openjdk-17 AS build

# Copy the source code into the container
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /target/atm-0.0.1-SNAPSHOT.jar atm.jar

# Expose the port that the application will listen on
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "atm.jar"]
