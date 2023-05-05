FROM ubuntu:latest AS build

RUN apt-get update

RUN apt-get install openjdk-17-jdk -y

COPY . .

# RUN chmod +x mvnw

RUN ./mvnw spring-boot:run

FROM openjdk:17-jre-slim

EXPOSE 8080

COPY --from=build /build/libs/AtmEndPoints.jar AtmApplication.jar



ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["java", "-jar", "app.jar"]