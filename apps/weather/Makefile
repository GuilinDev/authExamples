# Define a variable for the image name
IMAGE_NAME=weather-app

# Define the default make action
all: build

# Define how to build the Docker image
build:
	docker build -t $(IMAGE_NAME) .

# Define how to run the Docker container
run:
	docker run -p 8000:8000 $(IMAGE_NAME)

# Define how to stop the Docker container
stop:
	docker stop $(shell docker ps -q --filter ancestor=$(IMAGE_NAME))

# Define how to remove the Docker image
clean:
	docker rmi $(IMAGE_NAME)

# Define a help command
help:
	@echo "Makefile commands:"
	@echo "build - Build the Docker image"
	@echo "run   - Run the Docker container"
	@echo "stop  - Stop the Docker container"
	@echo "clean - Remove the Docker image"
