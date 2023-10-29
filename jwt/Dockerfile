# Use the official Golang image to create a build artifact.
FROM golang:1.21 as builder

# Set the Current Working Directory inside the container.
WORKDIR /app

# Copy go mod and sum files
COPY go.mod go.sum ./

# Download all dependencies.
RUN go mod download

# Copy the source code from the current directory to the Working Directory inside the container.
COPY . .

# Build the Go app
RUN go build -o main .

# Use the official Golang runtime as a parent image.
FROM golang:1.21

# Set the Current Working Directory inside the container.
WORKDIR /app

# Copy the pre-built binary from the builder
COPY --from=builder /app/main .

# Command to run the executable.
CMD ["./main"]
