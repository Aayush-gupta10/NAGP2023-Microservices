FROM openjdk:11

LABEL maintainer="aayush.gupta01@nagarro.com"

ADD target/booking-service-0.0.1-SNAPSHOT.jar booking-service-0.0.1-SNAPSHOT.jar

EXPOSE 8093

ENTRYPOINT ["java", "-jar","booking-service-0.0.1-SNAPSHOT.jar"]