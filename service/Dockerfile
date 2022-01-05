FROM openjdk:16-jdk-alpine
ARG JAR_FILE=target/service-0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]