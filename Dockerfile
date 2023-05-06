FROM adoptopenjdk/openjdk17:alpine-jre
WORKDIR /app
COPY target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]