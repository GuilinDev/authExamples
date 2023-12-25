FROM python:3.8-slim

LABEL maintainer="example@gmail.com"

# Update package repository and install ping and curl
RUN apt-get update && apt-get install -y \
    iputils-ping \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the client script into the container at /usr/src/app
COPY client.py ./

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir requests schedule

# Set the entrypoint to run the client script
ENTRYPOINT ["python3", "./client.py"]
