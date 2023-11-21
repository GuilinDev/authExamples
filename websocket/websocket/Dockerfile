# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="guilindev@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 18081 available to the world outside this container
EXPOSE 18081

# Add the application's jar to the container
ARG JAR_FILE=build/libs/websocket-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
