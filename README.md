# disneyworld-API
#### Description: A simple API to manage movies and characters from DisneyWorld.
## Introduction
This is a simple API developed in JAVA using SpringBoot to handle movies and characters from DisneyWorld.

## Design decisions
### Framework
Spring framework with SpringBoot is used
### Dependencies management
Maven is used to build and handle dependencies
### DataBase
The database engine used is H2. It is a lightweight fully embedded database engine 
obtained from maven repository using a pom dependency.

An in-memory database is used.
Of course, in a real life scenario an in-memory db does not make much sense, but it is
enough for our purposes here.

A very useful client named h2-console is available through any web browser, that allows
the user to interact with the db while it is running.

At startup the tables are created and populated with values obtained from some sql files 
in the resource folder.

### ORM
To fulfill the ORM necessities, JPA is used with its Hibernate implementation. No hibernate
specific annotations are used, just JPA.

### No Spring Data JPA
No Spring Data JPA dependency is used. This one may need a little explanation.

This project is one of my first API and I wanted to practice as much as possible.
So I decided to rely just on JPA (Hibernate implemented). All queries are created
using JPQL, no native SQL is used to provide as much as SQL agnosticism as possible.

No @Repository annotation nor JpaRepository or CrudRepository interfaces are used.
This decision led to a bit of boilerplate code in the DAO/Repository layer,
but I think it was worth it.

### API Documentation
The API is documented following the OpenAPI specification with Swagger.
Also, the swagger-ui is provided.

The Springdoc library is used.  

## Important SetUp information

### Swagger documentation
The swagger-ui is available at /swagger-ui.html.

### User's Authority
Two users are preloaded in the database:

    1. name = user  / password = user / autorithy = USER
    2. name = admin / password = admin / autorithy = ADMIN

All endpoints require authentication. 

Some endpoints require ADMIN authority: create new users, create or update movies or characters.

### H2-console
The H2-console is available at /h2-console