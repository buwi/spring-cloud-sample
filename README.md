# spring-cloud-sample

This is a sample Spring Cloud Stream application that uses RabbitMQ binder. It is built on Spring Cloud `Edgware.SR4` and Spring Boot `1.5.17.RELEASE`. 


The application requires a RabbitMQ server to point to. The following configuration will need to be configured:

`spring.cloud.stream.binders.rabbit.environment.spring.rabbitmq:`
```
                host: <my.rabbit.host>
                port: <my.rabbit.port>
                username: <my.rabbit.user>
                password: <my.rabbit.password>
```
https://stackoverflow.com/questions/53819877/spring-cloud-bus-fails-to-recover-after-network-outage