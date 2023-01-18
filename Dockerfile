FROM maven:3.8.1-openjdk-17-slim
WORKDIR /build
# Build dependency offline to streamline build
COPY . .
RUN mvn clean package -Pprod -DskipTests
FROM openjdk:11-jdk
COPY --from=0 /build/target/mygallery-0.0.1-SNAPSHOT.jar /app/target/mygallery-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/target/mygallery-0.0.1-SNAPSHOT.jar", "--server.port=8080" ]
