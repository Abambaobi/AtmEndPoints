FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw package

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/AtmEndPoints.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["java", "-jar", "app.jar"]