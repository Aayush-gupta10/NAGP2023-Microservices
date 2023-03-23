FROM openjdk:11

LABEL maintainer="anushka.agarwal@nagarro.com"

ADD target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar","api-gateway-0.0.1-SNAPSHOT.jar"]