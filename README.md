# eagl-service-walletmappings
Required to identify a wallet id for a given daad guide. 



## Building
We use our internal azure devops pipelines to build and package the application. The resulting docker 
images are published here in the Github  Container Registry

## Running
Running the Wallet API requires

* a running Enmeshed Connector (including its MongoDB)
* a running and configured ArangoDB  

You need to pass the application properties to the docker run command to establish a connection to the connector and database. 
There are several options: 

* pass individual environment variables
* define a separate application-<profile>.yaml and pass it to the docker run command
* pass it as json through the environment variable 

You can read about the options supported by Spring Boot [here](https://docs.spring.io/spring-boot/docs/2.6.x/reference/htmlsingle/#features.external-config)

### Setting up as container instance in Azure Cloud

The following example illustrates how to start this container as a service in the azure cloud. Switch to the azure console to run the following command: 

```
az container create --resource-group <<resource-group>> --name <<container-name>> \
 --image ghcr.io/bechtleav360/eagl-service-wallet:latest --dns-name-label <<dns-name>> \ 
 --ports 8080 --os-type Linux --protocol TCP --registry-username <<user to access registry>> \
 --registry-password <<password to access registry>> --environment-variables \
     SPRING_APPLICATION_JSON='{ "spring": { "profiles": { "active": "remote" }, \
 "security": { "user": {  "name": "xxx",  "password": "xxx" } } }, \
 "security": { "api-key": "xxx" }, \
 "arango": { "url": "xxx", "user": "xxx", "password": "xxx" }, \
 "enmeshed": { "connector": { "url": "xxx", "apikey": "xxx" }, "id": { "rt": "xxx" } } }'

```