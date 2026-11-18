FROM gradle:9.4.1-jdk17 AS build

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/notificacao-0.0.1-SNAPSHOT.jar /app/notificacao.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/notificacao.jar"]