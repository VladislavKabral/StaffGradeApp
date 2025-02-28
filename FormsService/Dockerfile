FROM openjdk:17

WORKDIR /application

COPY /target/FormsService-0.0.1-SNAPSHOT.jar forms-service.jar

ENTRYPOINT ["java", "-jar", "forms-service.jar"]
