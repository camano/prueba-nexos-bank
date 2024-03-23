FROM openjdk:17-alpine
COPY "./target/prueba-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 9000
ENTRYPOINT ["java","-jar","app.jar" ]