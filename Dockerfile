FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre as runtime

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Set environment variable for Spring Profile
ENV SPRING_PROFILES_ACTIVE=prod
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV PBK_SECRET_KEY=${PBK_SECRET_KEY}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
