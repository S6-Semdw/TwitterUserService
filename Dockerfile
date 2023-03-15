FROM maven:3.8.3-jdk-11 AS build-env

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

#RUN mvn spring-javaformat:help
#RUN ./mvnw spring-javaformat:help

COPY . ./

#RUN mvn spring-javaformat:apply
#RUN ./mvnw spring-javaformat:apply
RUN mvn package -DfinalName=TwitterUserService

FROM openjdk:11

EXPOSE 8080

WORKDIR /app

COPY --from=build-env /app/target/TwitterUserService-0.0.1-SNAPSHOT.jar ./TwitterUserService-0.0.1-SNAPSHOT.jar
CMD ["/usr/bin/java", "-jar", "/app/TwitterUserService-0.0.1-SNAPSHOT.jar"]