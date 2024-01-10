FROM gradle:7.6.3-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:17
ENV JAVA_OPTS "-Xms1g -Xmx1g"

RUN mkdir -p /srv/
COPY --from=builder /home/gradle/src/build/libs/*.jar /srv/app.jar
WORKDIR /srv

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]