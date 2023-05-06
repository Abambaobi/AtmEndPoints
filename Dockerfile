FROM openjdk:17-alpine
WORKDIR /app
COPY target/ /app/
CMD ["java", "-cp", "/app", "com.example.AtmApplication"]

