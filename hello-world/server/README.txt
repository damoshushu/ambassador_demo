Compiling:

mvn clean package -Pserver

Running the server:

mvn spring-boot:run -Pserver -Dspring.profiles.active=server 

Running the client:

mvn spring-boot:run -Pclient -Dspring.profiles.active=client 

Build server Docker image

mvn clean package
mvn dockerfile:build
