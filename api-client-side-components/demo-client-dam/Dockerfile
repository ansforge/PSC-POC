FROM maven:3-jdk-11 AS build
COPY settings-docker.xml /usr/share/maven/ref/
COPY pom.xml /usr/src/app/pom.xml
COPY demo-client-dam /usr/src/app/demo-client-dam
RUN mvn -f /usr/src/app/pom.xml -gs /usr/share/maven/ref/settings-docker.xml clean package

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/demo-client-dam/target/demo-client-dam-*.jar /usr/app/demo-client-dam.jar
USER daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/demo-client-dam.jar"]
