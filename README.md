# Kapi: Reserva

A simple REST API for making reservations or bookings written in kotlin.

## Overview

The choice of technology is the following:

  * Postgresql 14
  * Make 3.81
  * Java 22
  * Gradle 8.9
  * Kotlin 1.9.10-459
      * Exposed (ORM)
      * Ktor (Http)
      * Netty (Server)
      * Koin (IoC, Dependency Injection)

### Database

For our RDMS we'll be using Postgres 14, it is assumed that the psqld service is reachable and that we have access to 
a role that can login and create tables and perform inserts.

The database is comprised of 3 directories, the `init`, `model` and the `data` directory, `model` and the `data` directory.

  * `init` contains account creation PSQL statements
  * `model` contains data definition PSQL statements, mainly setups tables, and constraints
  * `data` contains the "seed" data

We've included a simple makefile to support database setup. The database is named `reserva` ("reservation" in Spanish), 
and the user is `kapi`. To initialize the database do the following:

```shell
dropdb reserva
cd database/psql
make init model data
```

#### Note about ORM

This project uses the exposed kotlin ORM

  * https://jetbrains.github.io/Exposed/home.html

> **NOTE:** While the ORM supports SQL generation, we opted to not use such functionality to show how the database could be 
generated manually. On larger projects you'd probably want to use your chosen ORM's migration and generation feature
> set if available.

### App

The application is written in kotlin, uses koin for DI and exposed for ORM or sql querying.

Generally speaking the domain can be modeled by the following entities:

  * Diner - a person eating at the restaurant
  * Restaurant - a place that serves food and that can host patrons
  * Table - A 2, 4, 6, seating tables where patrons can eat.
  * Reservation - an entry that reserves a set of tables of a restaurant at a given time
  * Dietary Restriction - in the case of diners it is foods that cannot be consumed by such diners; in the case of
    restaurants it is an endorsement that the restaurant has a menu friendly for diners with such restrictions.

The application follows a repository and service structure; it does not attempt have a clean architecture as it'd be 
overly complex given the functionality included. However, repository and services provides us with enough abstraction
to provide simple mocking and testable implementations.

Dependency Injections is configured at the app root and there is a very simple http routing functions to allow 
for 2 endpoints:

  * Find restaurants
  * Create a reservation

#### Testing

In this project, unit testing is only done for some utility functions, as way to show testing, however in a larger
project we'd want to have coverage using a mock framework such as [MockK](https://mockk.io) and used to mock 
services and repository to validate underlying implementation.

## API Endpoints

### Finding a restaurant

```shell
curl -vvv \
  -H 'Accept: application/json' \
  http://localhost:8080/restaurant/find\?diner\=1\&datetime\=2024-07-25T17:32:00 
```

### Creating a reservation

```shell
curl -vvv \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  http://localhost:8080/reservation \
  -d '{
    "restaurantId": 1,
    "dinerIds": [1],
    "atDatetime": "2024-07-25T17:32:00"
}'
```




