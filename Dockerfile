FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} fitOfferApp-0.1.0.jar
EXPOSE 8083

ENTRYPOINT ["java","-jar","/fitOfferApp-0.1.0.jar"]