# Makefile

# Define variables for commands
DOCKER_COMPOSE = docker-compose
FED_BUILD = ./idempotenceEndpoint/gradlew -p ./idempotenceEndpoint

# Build the Spring Boot application and Docker images, then start the services
up:
	$(FED_BUILD) clean build
	$(DOCKER_COMPOSE) up --build

# Start the services without building
start:
	$(DOCKER_COMPOSE) start

# Stop the running services
stop:
	$(DOCKER_COMPOSE) stop

# Bring down the services and remove containers
down:
	$(DOCKER_COMPOSE) down

# Remove the services and their associated volumes
clean:
	$(DOCKER_COMPOSE) down -v

.PHONY: up start stop down clean
