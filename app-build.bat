call eureka-server-build.bat
cd ../
call api-gateway-build.bat
cd ../
call forms-service-build.bat
cd ../
call users-service-build.bat
cd ../
call packages-service-build.bat
cd ../
call docker-compose up -d