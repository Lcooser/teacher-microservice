
FROM ubuntu:latest as build


RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven


WORKDIR /teacherapi


COPY . .


RUN mvn clean package -DskipTests

# Etapa 2: Execução
FROM openjdk:21-jdk-slim

EXPOSE 8080


COPY --from=build /teacherapi/target/teacherapi-0.0.1-SNAPSHOT.jar app.jar


LABEL authors="professor"


ENTRYPOINT ["java", "-jar", "app.jar"]
