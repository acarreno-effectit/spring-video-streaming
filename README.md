# spring-video-streaming

## Steps to run the application

### 1. Prepare data store:
  - Create instance of Postgres SQL using Docker, using a command line interface run the following sentences:
  ```
  cd src/main/resources
  docker-compose -up -d
  ```
  - Once the instance of Postgres is created it is necessary to create the data structure in the database, using any Postgres Client as PG Admin or other, connect using the credentials 'user=admin' and 'password=Test123*'. After that, execute the script file DDL.sql  which is found in *src/main/resources*.

### 2. Run application:
  - Clone project from GitHub using command line interface:
  ```
  git clone https://github.com/acarreno-effectit/spring-video-streaming.git
  ```
  - Run application using command line interface:
  ```
  mvn spring-boot:run
  ```
  >NOTE: It's required to have MAVEN cli installed as a *Environment Variable*
  

### 3. API Documentation:
- Swagger API documentation can be located in the URL:
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
- Postman API Documentation:
https://documenter.getpostman.com/view/1764904/2s9YJgTLRv
