# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the necessary files to the container
COPY target/classes /app/classes
COPY target/generated-sources /app/generated-sources
COPY target/generated-test-sources /app/generated-test-sources
COPY target/test-classes /app/test-classes

# Expose port 8080 for the container
EXPOSE 8080

# Run the application
CMD ["java", "-cp", "/app/classes:/app/generated-sources:/app/generated-test-sources:/app/test-classes", "com.example.AtmApplication"]
