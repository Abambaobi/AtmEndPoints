FROM maven:3.8-jdk-11-slim AS build

COPY . /app

WORKDIR /app

RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=build /app/target/Atm.jar /app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production

CMD ["java", "-jar", "/app.jar"]