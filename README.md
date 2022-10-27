# disneyworld-API
#### Description: A simple API to manage movies and characters from DisneyWorld.
## Introduction
This is a simple API developed in JAVA using SpringBoot to handle movies and characters from DisneyWorld.

## Design choices
### Framework
SpringBoot is used as the main framework.

References:
1. https://spring.io/projects/spring-framework
2. https://spring.io/projects/spring-boot

### Dependencies management
Maven is used to build and handle dependencies.

References:
1. https://maven.apache.org/

### ORM
Hibernate is the ORM framework used. No hibernate specific annotations are used, just JPA.

References:
1. https://jakarta.ee/specifications/persistence/
2. https://hibernate.org/

### No Spring Data JPA
No Spring Data JPA dependency is used. This one may need a little explanation.

This project is one of my first API and I wanted to practice as much as possible.
So I decided to rely just on JPA (Hibernate implemented). 

No @Repository annotation nor JpaRepository/CrudRepository interfaces are used.
This decision led to a bit of boilerplate code in the DAO/Repository layer,
but I think it was worth it.

Also, although I can see the benefits from the labour reduction that Spring Data JPA
provides regarding the repetitive code involved in CRUD repositories, I am not yet completely
convinced about its use. Spring Data JPA introduces another abstraction layer above JPA, 
Hibernate and JDBC that comes at certain price. For a more detailed analysis of this idea,
check the following article: 
<a href="https://itnext.io/advantages-of-not-using-spring-data-and-hibernate-with-relational-data-8a509faf0c48" target="blank">Advantages of not using Spring Data and Hibernate with relational data</a>.

All queries are created using JPQL, no native SQL is used to provide as much as SQL
agnosticism as possible.

References:
1. https://jakarta.ee/specifications/persistence/

### API Documentation
The API is documented following the OpenAPI specification with Swagger.
Also, the swagger-ui is provided to enable a friendly interaction with the API.

The Springdoc library is used.  

References:
1. https://www.openapis.org/
2. https://swagger.io/
3. https://springdoc.org/

### JavaDoc Documentation and Design by Contract
All interfaces and most classes are documented using javadoc. Documentation was written
following some Design by Contract (DbC) concepts: preconditions, postconditions, contracts, etc. 

Considering the documentation a major part of the software and following the Design By 
Contract guidelines, no defensive programming techniques are used. 
So, preconditions are not checked inside a method, rather they are considered to be true at 
invocation time. It is the client responsibility to invoke a method fulfilling its
preconditions. If a precondition is not valid, the method is freed from its contractual
responsibility and its behaviour is undetermined.

A consequence of this programming technique is that exceptions are raised not because 
of the failure to meet the preconditions at invocation time but for those cases that, 
being the preconditions true, the method is unable to meet its postconditions.

References: 
1. "Meyer - Object-oriented software construction - 2nd ed"
2. "Robillard - Introduction to Software Design with Java"
3. "Liskov - Program development in Java"

### DTO
Data Transfer Objects (DTO) are used to hold data that needs to be transfer from the client to the server. 
The domain objects are not transferred directly to the client, instead a simplified version of the data 
contained in the domain objects are transferred in the form of DTOs. This technique allows to decouple the 
internal representation of the domain objects and the data handled by the API.

DTOs are serializable, as they must be transferred through a wire (network). 
DTOs are little more than a bunch of fields and the getters and setters for them. The fields in a DTO are
simple, being primitives, simple classes like strings and dates, or other DTOs.

I also used DTOs to indirectly solve a well-known problem: the infinite recursion that arises from the
serialization of objects with bidirectional relationships. When an object holds a reference
to another object that also holds a reference to the first object (bidirectional association or relation) 
special care must be exercised to avoid infinite indirect recursion calls (stackoverflow). 

In this project some domain objects have bidirectional relationships between them, if serialization is 
attempted without care an infinite recursion loop it is very likely to occur. 
Indeed, if the domain objects were sent directly to the client the infinite recursion would happen when
the parser tries to serialize the objects to JSON or XML (Springboot uses Jackson as the default parser).
Of course, there are ways to instruct the serializer to avoid these recursive calls, but I applied what I
think is a more elegant solution to solve this problem: instead of trying to serialize objects that hold 
bidirectional relations, serialization is performed over simple DTOs that do not have such relations. 
The references to other objects held by the domain objects are eliminated in the DTOs.

References:
1. Fowler - Patterns of Enterprise Application Architecture
2. Yener - Professional Java EE Design Patterns
3. Sun Microsystems - Core J2EE Patterns - 2nd ed

### Exceptions as a way to handle non-successful http responses
Traditional Java practice tell us that exceptions should be reserved for exceptional
cases and should not be used for normal or expected situations.
Bloch says: "Exceptions are, as their name implies, to
be used only for exceptional conditions; they should never be used for
ordinary control flow. More generally, use standard, easily recognizable idioms
in preference to overly clever techniques that purport to offer better performance.
Even if the performance advantage is real, it may not remain in the face of steadily
improving platform implementations. The subtle bugs and maintenance headaches
that come from overly clever techniques, however, are sure to remain.
This principle also has implications for API design. A well-designed API
must not force its clients to use exceptions for ordinary control flow."

According to Bloch's view, exceptions should not be used as a mean to handle an expected
situation, for example a request for entity not found.

Another view is that exceptions are a way to inform the client of a method (the caller),
some particular situation that should be distinguished from others.
Liskov says: "An important point is that exceptions are not synonymous with errors.
Exceptions are a mechanism that allows a method to bring some information
to the attention of its caller. That information might not concern an error. 
For example, there isn’t anything erroneous about search being called on an element that
isn’t in an array; instead, this is just an interesting situation that the caller should be 
informed about. We convey this information through an exception because we want to distinguish 
it from the other possibility. The classification of one possibility as normal and the others 
as exceptional is somewhat arbitrary."

Liskov's view aligns better with the standard SpringBoot practice to handle non-successful 
http response situations by raising custom exceptions.
Such technique is used in this project: If something goes wrong with a request, a custom
exception is raised (mainly in the service layer) that will be caught by an exception handler. The
exception handler is in charge of catching the exception and returning the apropiate http response 
entity. 

References:

1. Liskov - Program development in Java
2. Bloch - Effective Java - 3th ed
3. Martin - Clean code

### Custom exceptions: checked or unchecked
Bloch says: "The cardinal rule in deciding whether to use a checked or an unchecked
exception is this: use checked exceptions for conditions from which the caller
can reasonably be expected to recover. By throwing a checked exception, you
force the caller to handle the exception in a catch clause or to propagate it
outward". "There are two kinds of unchecked throwables: runtime exceptions and errors.
They are identical in their behavior: both are throwables that needn’t, and
generally shouldn’t, be caught. If a program throws an unchecked exception or an
error, it is generally the case that recovery is impossible and continued execution
would do more harm than good. If a program does not catch such a throwable, it
will cause the current thread to halt with an appropriate error message. 
Use runtime exceptions to indicate programming errors."

Robert Martin says: "The debate is over. For years Java programmers have debated over the 
benefits and liabilities of checked exceptions". "We have to decide—really—whether checked
exceptions are worth their price. What price? The price of checked exceptions is an Open/Closed
Principle violation.
If you throw a checked exception from a method in your code and the catch is three levels
above, you must declare that exception in the signature of each method between you and
the catch. This means that a change at a low level of the software can force signature
changes on many higher levels. The changed modules must be rebuilt and redeployed,
even though nothing they care about changed". "Checked exceptions can sometimes be useful if you are writing a critical library: You
must catch them. But in general application development the dependency costs outweigh
the benefits."

I decided to go with Robert Martin and use unchecked exceptions (extending RuntimeException).

1. Bloch - Effective Java - 3th ed
2. Martin - Clean code

### DataBase
The database engine used is H2. It is a lightweight fully embedded in Java database
engine which can be easily obtained from the maven repository including a pom dependency.

An in-memory database is used.
Of course, in a real life scenario an in-memory db does not make much sense, but it is
enough for my purposes here.

A very useful client named h2-console is available through any web browser, that allows
the user to interact with the db while it is running.

At startup the tables are created and populated with values obtained from some sql files
located at the resource folder.

References:
1. https://www.h2database.com/html/main.html

### DB Rollbacks
If during a save or update operation something goes wrong, the db maintains its consistency.

Every save or update operation is considered atomic and interacts with the db in its own transaction.
So, if something goes wrong during such operations (for instance, if the user fails to provide some mandatory
information and an exception is raised), the whole transaction rollbacks and the db maintains its consistency.

### Security
Spring Security is used to provide authentication and authorization capabilities.
Basic Access Authentication is used (HTTP Basic).

## Important SetUp information

### Server port
The server port is configured to be 8080. If you have that port already in use, consider 
modify the application.properties file.

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

## TODO's
Some requirements not implemented yet:

    1. Image handling: So far the image is represented as a string.

    2. E-mail sendig when a new user is registered.

    3. Authentication using a token, for instance jwt. So far authentication is performed with http basic method. 

    4. Unit testing with JUnit