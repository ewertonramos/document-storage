# Document storage
This service provide rest endpoints to store in memory any kind of document.

## Requirements
Set up both maven and java to the PATH environment variable.
- Maven version 3.6.3 available [here](http://maven.apache.org/download.cgi)
- Java version 15 available [here](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)


## How to run SpringBoot version



Run:
```
mvn spring-boot:run
```

The service will be running at `localhost:8080/v1.0/`


## How to run Servlet version

Run:
```
mvn clean package
```

Deploy the generated .war file in you favourite Java Web Container.
The service will be running at `localhost:8080/`