# spring-tour-backend

This is a maven project.
So the code can be imported in eclipse as a maven project or maven 
can be used to build the source from eclipse.

If want to use command line then run command in base directory:
mvn clean install

Application can be run:

1. Within eclipse, run Application.java as main class
2. After building jar using maven, use command java -jar <jarname.jar>

Port of the Application is: 8090

1. http://localhost:8090/tours/refresh 
2. http://localhost:8090/tours

Jacoco is configured for test coverage.

Eclipse coverage as option can be also used for test coverage.
Or after building the source using maven, go to target directory, then inside jacoco-ut folder check
index.html for test coverage



