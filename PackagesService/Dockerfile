FROM openjdk:17

WORKDIR /application

COPY /target/PackagesService-0.0.1-SNAPSHOT.jar packages-service.jar

ENTRYPOINT ["java", "-jar", "packages-service.jar"]
