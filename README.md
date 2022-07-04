# CRUD

Java (Spring Boot) CRUD application

## Build and Run

```bash
# Pull the Postgres docker image
docker pull postgres:11

# Run the Postgres server container
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -d postgres:11

# Create a new Postgres database instance
docker exec dev-postgres psql -U username -c"CREATE DATABASE cruddb" postgres

# Run the app (in the `crud` directory):
gradlew bootRun
```

## HTML Endpoints

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8080/ | Root (Authors) page |
|`GET`|http://localhost:8080/book | Books page |
