# REST API mini project

This projects gets book information from external [Ice & Fire API](https://anapioficeandfire.com/Documentation#books).\
It also provides APIs for CRUD operations of a book - baked by an in-memory database.

### Technology stack
* Java-8
* Spring Boot
* Spring JPA for database operations
* In-memory H2 database

### Design detail
* Adhere to SOLID design principles.
* Uses design patterns like:
    * Gateway
    * Mapper
    * Null Object
    * Singleton

### Test coverage
* 100% classes, 97% lines covered in test cases
* All functions are being tested.

### Note

```
Strict JSON checking enabled, REST call will fail if valid JSON is not passed.
Also pass content-type as application/json while sending data.
```

## Getting started

### Default setup

By default, it uses in-memory H2 database, hence no configuration is required.\
Switch to another database can be configured in application.properties file.

```console
server.port=8080

ice.and-fire.api.base.url=https://www.anapioficeandfire.com/api
ice.and-fire.api.books=/books

spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa

```

### Run on Mac / Linux

```console
$ cd adeva/bin
$ sh startServer.sh
```


### Run on Windows

```console
> cd adeva\bin
> startServer.bat
```

### Run using Maven after making code/config changes

```console
mvn spring-boot:run
```

### Sample APIs
```vim

# Get external book
$ curl -X GET "http://localhost:8080/api/external-books?name=A%20Game%20of%20Thrones"

# Post a book
$ curl -H "Content-Type: application/json" -X POST -d '{"name":"My First Book","isbn":"123-3213243567","authors":["John Doe"],"number_of_pages":350,"publisher":"Acme Books","country":"United States","release_date":"2019-08-01"}' "http://localhost:8080/api/v1/books"

# Get all books
$ curl -X GET "http://localhost:8080/api/v1/books"

# Get book by id
$ curl -X GET "http://localhost:8080/api/v1/books/1"

# Search book by name
$ curl -X GET "http://localhost:8080/api/v1/books?name=My%20First%20Book"

# Search book by publisher
$ curl -X GET "http://localhost:8080/api/v1/books?publisher=Acme%20Books"

# Search book by country
$ curl -X GET "http://localhost:8080/api/v1/books?country=United%20States"

# Search book by release_date
$ curl -X GET "http://localhost:8080/api/v1/books?release_date=20190801"

# Update a book
$ curl -H "Content-Type: application/json" -X PATCH -d '{"name":"My First Updated Book"}' "http://localhost:8080/api/v1/books/1"

# Delete a book
$ curl -H "Content-Type: application/json" -X DELETE "http://localhost:8080/api/v1/books/1"

```