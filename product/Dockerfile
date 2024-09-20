FROM eclipse-temurin:17-jre-alpine@sha256:e7b28b772a5aaa0dc759c540ffcf1670765c1ae0245d41d9995db8a3a6115bd7
WORKDIR /app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY ./build/libs/maverick-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
RUN chown -R javauser:javauser .
USER javauser
ENTRYPOINT ["java","-Dspring.profiles.active=default,local","-jar","app.jar"]