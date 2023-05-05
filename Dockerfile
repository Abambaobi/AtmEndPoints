

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