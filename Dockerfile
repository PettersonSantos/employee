FROM openjdk:17-jdk-alpine
ADD target/employee-0.0.1-SNAPSHOT.jar employee.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /employee.jar"]