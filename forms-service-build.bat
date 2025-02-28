cd FormsService
call mvn clean package -DskipTests
call docker build -t forms-service:0.0.1 .