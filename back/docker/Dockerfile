# 첫 번째 스테이지: 빌드 스테이지
FROM gradle:7.6.3-jdk17 AS builder

WORKDIR /app

COPY ../gradlew ./gradlew
COPY ../gradle ./gradle
COPY ../build.gradle ./build.gradle
COPY ../settings.gradle ./settings.gradle

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY ../src ./src
RUN ./gradlew build --no-daemon -x test

# 두 번째 스테이지: 실행 스테이지 (Amazon Corretto 17 사용)
FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/build/libs/back-0.0.1-SNAPSHOT.jar app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "app.jar"]
