FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/API-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
