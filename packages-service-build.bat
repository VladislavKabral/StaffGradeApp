cd PackagesService
call mvn clean package -DskipTests
call docker build -t packages-service:0.0.1 .