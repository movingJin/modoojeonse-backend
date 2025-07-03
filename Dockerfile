FROM openjdk:17-slim
LABEL maintainer="shdlehdwls@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 58083
ENTRYPOINT ["java", "-Djava.security.egd=file:dev/./uuradom", "-jar","/app.jar"]
