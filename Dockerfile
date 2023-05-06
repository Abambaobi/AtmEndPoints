FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.class /app/
CMD ["java", "AtmApplication"]
