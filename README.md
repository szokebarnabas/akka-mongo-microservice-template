###Microservice template project

Package the application:
sbt package

Build docker image:
sbt docker:publishLocal

Run docker image:
docker run -p 8080:8080 advert-service:1.0