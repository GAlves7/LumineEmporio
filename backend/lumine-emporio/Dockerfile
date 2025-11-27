FROM eclipse-temurin:17-jdk

RUN mkdir -p /app/imagens
WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]