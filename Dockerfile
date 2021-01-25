FROM openjdk:11.0.9.1
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /ordermanagement.jar
ENTRYPOINT ["java","-jar","/ordermanagement.jar"]