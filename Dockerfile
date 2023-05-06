FROM openjdk:17-alpine
VOLUME /tmp
COPY target/atm-0.0.1-SNAPSHOT.jar /app/atm.jar
ENTRYPOINT ["java", "-jar", "/app/atm.jar"]


