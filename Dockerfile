FROM amazoncorretto:21.0.6-alpine3.18
LABEL authors="petrohub"
WORKDIR /app
COPY target/petrohub_project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]