build:
	go build -o main .

run:
	go run .

docker-build-image:
	docker build -t my-go-app .

docker-build:
	docker-compose build

docker-up:
	docker-compose up

docker-down:
	docker-compose down

docker-run:
	docker run --rm -p 8080:8080 my-go-app

.PHONY: build run docker-build docker-up docker-down
