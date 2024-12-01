FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y ca-certificates && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/ticketBooking-0.0.1-SNAPSHOT.jar .

#them folder anh vao docker
COPY uploads/ /app/uploads/

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/ticketBooking-0.0.1-SNAPSHOT.jar"]