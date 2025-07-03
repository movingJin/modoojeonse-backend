# Modoojeonse Backend

A Springboot web service for the **Modoojeonse** platform.

## Installation / Quick Start
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![macOS](https://img.shields.io/badge/mac%20os-000000?style=for-the-badge&logo=macos&logoColor=F0F0F0)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)

### Project Structure
```
modoojeonse-frontend/
├── gradle/
├── src/
└── ...
```

### 1. Create a `yml` file

Add a `application-prod.yml` or `application-dev.yml` file on the project ./src/resources/ directory to define configuration:

```
server:
  port: { service_port }
  error:
    include-message: always

spring:
  config:
    activate:
      on-profile: prod

  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://{ host }/{ database }
    username: { username }
    password: { password }
  jpa:
    generate-ddl: true
  data:
    redis:
      host: { host }
      port: { port }
      password: { password }
  mail:
    host: smtp.gmail.com
    port: 587
    username: { email }
    password: { password }
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
    auth-code-expiration-millis: 1800000  # 30 * 60 * 1000 == 30분
  elasticsearch:
    username: { username }
    password: { password }
    uris: { host }

jwt:
  secret:
    key: { secret_key }

logging:
  level:
    root: INFO

```


### 2. Build the Project

Build the java project with gradle and move generated `jar` file on the root directory:

```
$ ./gradlew clean build --exclude-task test
$ mv build/libs/modoojeonse-0.0.1-SNAPSHOT.jar ./
```

### 3. Deploy with Docker

Use Docker Compose to build and run the deployment:

```
$ docker-compose build
$ docker-compose up -d
```
