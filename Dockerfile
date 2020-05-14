FROM openjdk:8-jdk-alpine
ADD target/BazaDanych-0.0.1-SNAPSHOT.jar .
EXPOSE 7000
CMD java -jar BazaDanych-0.0.1-SNAPSHOT.jar