FROM openjdk:17-alpine
WORKDIR /app
COPY target/classes/com/example/AtmApplication.class /app/
CMD ["java", "target.classes.com.example.AtmApplication"]
