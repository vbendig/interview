FROM eclipse-temurin:17-jre-alpine@sha256:e7b28b772a5aaa0dc759c540ffcf1670765c1ae0245d41d9995db8a3a6115bd7
WORKDIR /app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY ./build/libs/eurekaserver-0.0.1-SNAPSHOT.jar app.jar
RUN chown -R javauser:javauser .
USER javauser
ENTRYPOINT ["java","-jar","app.jar"]