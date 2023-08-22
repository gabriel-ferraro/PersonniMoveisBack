FROM maven:3.8.4-openjdk-17-slim AS build
COPY . .
RUN mvn clean install -DskipTests
EXPOSE 8081
CMD ["mvn", "spring-boot:run"]
