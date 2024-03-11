FROM openjdk:21-slim

COPY target/Hello-0.0.1-SNAPSHOT.jar actions-trial.jar
ENTRYPOINT ["java","-jar","/actions-trial.jar"]