## About this project

This project is a demo for Baxter.

## Localhost quickstart

1. Please build this app first by using `./build-dev.sh`
2. You can launch application by using this command `./mvnw` 


## Project Structure

- `/src/*` structure follows default Java structure.
- `/src/main/java` - Java source folder
- `/src/main/resources` - Java resources folder
- `/src/main/resources/config` - Spring boot application configuration files
- `/src/test/*` - Folder for unit/IT/performance tests

## Development

### Helper scripts

- `./build-dev.sh` - starts a developer build


## Testing

### Spring Boot tests

To launch your application's tests, run:

```
./mvnw verify
```

