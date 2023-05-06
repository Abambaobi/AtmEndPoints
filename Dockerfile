FROM openjdk:17-alpine
WORKDIR /app
COPY target/classes/com/example/*.class /app/
CMD ["java", "com.example.AtmApplication"]

