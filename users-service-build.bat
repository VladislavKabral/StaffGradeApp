cd UsersService
call mvn clean package -DskipTests
call docker build -t users-service:0.0.1 .