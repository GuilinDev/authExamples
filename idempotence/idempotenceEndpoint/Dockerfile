FROM azul/zulu-openjdk:17

LABEL maintainer="guilindev@gmail.com"

# Update package repository and install ping and curl
RUN apt-get update && apt-get install -y \
    iputils-ping \
    curl \
    && rm -rf /var/lib/apt/lists/*

VOLUME /tmp

# Make port 8082 available to the world outside this container
EXPOSE 8082

ARG JAR_FILE=build/libs/idempotenceEndpoint-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
