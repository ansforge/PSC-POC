FROM maven:3-jdk-11 AS build
COPY settings-docker.xml /usr/share/maven/ref/
COPY front-esignsante /usr/src/app/front-esignsante
COPY client-webservices /usr/src/app/client-webservices
COPY pom.xml /usr/src/app
COPY .openapi-generator-ignore /usr/src/app/
RUN mvn -f /usr/src/app/pom.xml -gs /usr/share/maven/ref/settings-docker.xml clean package -Dmaven.test.skip=true

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/front-esignsante/target/esignsante-psc-*.jar /usr/app/esignsante-psc.jar
USER daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/esignsante-psc.jar"]



