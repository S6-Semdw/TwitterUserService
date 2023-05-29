FROM openjdk:19
MAINTAINER SemdeWilde
COPY target/TwitterUserService-0.0.1-SNAPSHOT.jar TwitterUserService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/TwitterUserService-0.0.1-SNAPSHOT.jar"]
