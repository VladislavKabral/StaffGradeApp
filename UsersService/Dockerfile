FROM openjdk:17

WORKDIR /application

COPY /target/UsersService-0.0.1-SNAPSHOT.jar users-service.jar

ENTRYPOINT ["java", "-jar", "users-service.jar"]
