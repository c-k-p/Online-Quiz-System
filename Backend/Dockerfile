FROM eclipse-temurin:11

COPY target/online-quiz-maker-0.0.1-SNAPSHOT.jar /usr/app/app.jar

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "app.jar"]
