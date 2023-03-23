FROM openjdk:11

LABEL maintainer="anushka.agarwal@nagarro.com"

ADD target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery-0.0.1-SNAPSHOT.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar","service-discovery-0.0.1-SNAPSHOT.jar"]