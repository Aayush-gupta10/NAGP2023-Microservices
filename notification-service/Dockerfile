FROM openjdk:11

LABEL maintainer="aayush.gupta01@nagarro.com"

ADD target/notification-service-0.0.1-SNAPSHOT.jar notification-service-0.0.1-SNAPSHOT.jar

EXPOSE 8095

ENTRYPOINT ["java", "-jar","notification-service-0.0.1-SNAPSHOT.jar"]