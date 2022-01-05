FROM openjdk:17-jdk-alpine as nifobot-service
ARG JAR_FILE=service/build/nifobot-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17-jdk-alpine as nifobot-datamodel
ARG JAR_FILE=datamodel/build/nifobot-datamodel.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]