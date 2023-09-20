# SpringBoot with AMQ Core Protocol
Sample SpringBoot based application integration with Red Hat AMQ through ``CORE/JMS`` protocol. This application is configured produce and consume the messages from two different brokers. 

## Configuration
Configuration are based on the provided connection options in the application.properties.
````properties
#1st Broker Configuration
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=secret
spring.activemq.queue=springQueue
#2nd Broker Configuration
spring.wmq.broker-url=tcp://localhost:61617
spring.wmq.user=admin
spring.wmq.password=secret
````

## Build Process
This application is configured with the following dependency which is referenced through Red Hat repository.
- Add the Red Hat repository to your Maven settings or POM file.
    ````xml
    <repository>
      <id>red-hat-ga</id>
      <url>https://maven.repository.redhat.com/ga</url>
    </repository>
    ````
- Build using ``mvnw`` command
    ````shell
    ./mvnw spring-boot:run
    ````
## Run the application
````shell
./mvnw spring-boot:run
````
## Test the Application
- Send a message over a curl command
    ````shell
    curl --location 'http://localhost:8080/post-message' --header 'Content-Type: application/json' --data '{"name":"Sid", "age": "30" }'
    ````

- Check the message using get api
    ````shell
    curl --location 'http://localhost:8080/get-message' --header 'Content-Type: application/json' | jq
    [
      {
        "name": "Sid",
        "age": "30"
      }
    ]
    ````
