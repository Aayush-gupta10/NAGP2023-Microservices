FROM openjdk:11

LABEL maintainer="aayush.gupta01@nagarro.com"

ADD target/search-service-0.0.1-SNAPSHOT.jar search-service-0.0.1-SNAPSHOT.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar","search-service-0.0.1-SNAPSHOT.jar"]