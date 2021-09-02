FROM openjdk:11
#FROM adoptopenjdk:11-jre-hotspot
#FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/orders-*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]