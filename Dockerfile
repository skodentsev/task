FROM gradle:6.7.1-jdk11 as build

WORKDIR /opt/app
COPY build.gradle ./
COPY src ./src

RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /opt/app
COPY --from=build /opt/app/build/libs/app.jar ./task-app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-app.jar"]