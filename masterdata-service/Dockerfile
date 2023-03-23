FROM openjdk:11

LABEL maintainer="aayush.gupta01@nagarro.com"

ADD target/masterdata-service-0.0.1-SNAPSHOT.jar masterdata-service-0.0.1-SNAPSHOT.jar

EXPOSE 8091

ENTRYPOINT ["java", "-jar","masterdata-service-0.0.1-SNAPSHOT.jar"]