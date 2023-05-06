FROM openjdk:17-alpine
WORKDIR /app
COPY . /app
CMD ["java", "-cp", "target/classes", "com.example.AtmApplication"]