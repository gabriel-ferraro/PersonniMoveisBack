# Estágio 1: Construção da aplicação
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests

# Estágio 2: Execução da aplicação
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/personniMoveis-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
