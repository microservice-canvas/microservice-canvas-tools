FROM java:openjdk-8u91-jdk
ARG VERSION
ENTRYPOINT ["java", "-jar", "microservice-canvas-cli.jar"]
COPY build/libs/microservice-canvas-cli-${VERSION}.jar microservice-canvas-cli.jar
