# Use a imagem base do Ubuntu
FROM ubuntu:latest

# Instale o OpenJDK 21
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk

# Instale o gnupg2 e wget necessários
RUN apt-get install -y gnupg2 wget

# Instale o PostgreSQL 16 a partir dos repositórios do Ubuntu
RUN apt-get install -y postgresql-16

# Copie o arquivo JAR da aplicação para o contêiner
COPY target/teacherapi-0.0.1-SNAPSHOT.jar /app/teacherapi.jar

# Defina o comando de entrada
ENTRYPOINT ["java", "-jar", "/app/teacherapi.jar"]
