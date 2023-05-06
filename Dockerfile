FROM openjdk:17-alpine
VOLUME /tmp
RUN wget https://drive.google.com/u/0/uc?id=1qQtL8gqoQ-bQfaGrcnJXDsvxPqFiqb4Z&export=download -O /app/atm.jar
ENTRYPOINT ["java", "-jar", "/app/atm.jar"]

