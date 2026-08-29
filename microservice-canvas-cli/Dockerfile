FROM eclipse-temurin:17-jre
ARG VERSION
ENTRYPOINT ["java", "-jar", "microservice-canvas-cli.jar"]
COPY build/libs/microservice-canvas-cli-${VERSION}.jar microservice-canvas-cli.jar
