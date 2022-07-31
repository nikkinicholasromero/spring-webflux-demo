```
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=nikkinicholasromero/spring-boot-demo:latest
docker run --rm -p 8080:8080 nikkinicholasromero/spring-boot-demo:latest
```