FROM maven:3-jdk-11 AS build
COPY settings-docker.xml /usr/share/maven/ref/
COPY pom.xml /usr/src/app/pom.xml
COPY asip-sign-api /usr/src/app/asip-sign-api
COPY webservices /usr/src/app/webservices
RUN mvn -f /usr/src/app/pom.xml -gs /usr/share/maven/ref/settings-docker.xml -DskipTests clean package

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/webservices/target/esignsante-*.jar /usr/app/esignsante.jar
USER daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/esignsante.jar"]