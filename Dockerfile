FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -Dskip.ut=true -Dskip.it=true

FROM eclipse-temurin:23_37-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chmod +x app.jar
ENTRYPOINT ["java", "-jar", "app.jar"] 