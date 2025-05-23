# Quick start

## Environment setup
```
npm install
```

## Build Vaadin frontend
```
./gradlew clean vaadinPrepareFrontend
```

## Run Postgres
```
docker compose -f src/main/docker/docker-compose.yaml up -d
```

## Create the DB schema
Execute `/src/main/db/schema_creation.sql`

## Insert some sample data into the DB
Execute `/src/main/db/data_generation.sql`

# Run the app
From the command line:
```
./gradlew run
```

OR...

From IntelliJ:
- Open `/src/main/kotlin/com/dankim/Main.kt`
- Run the `main()` function
