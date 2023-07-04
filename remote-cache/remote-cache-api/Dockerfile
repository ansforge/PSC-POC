FROM maven:3-jdk-11 AS build
COPY remote-cache-api/src /usr/src/app/src
COPY remote-cache-api/pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/target/remote-cache-api*.jar /usr/app/remote-cache-api.jar
RUN mkdir -p /app/json-schemas-repo
RUN chown -R daemon: /app/json-schemas-repo
USER daemon
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/remote-cache-api.jar"]
