FROM openjdk:11

LABEL maintainer="aayush.gupta01@nagarro.com"

ADD target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar

EXPOSE 9998

ENTRYPOINT ["java", "-jar","api-gateway-0.0.1-SNAPSHOT.jar"]