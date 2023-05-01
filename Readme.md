# Spring boot webfux contract first implementation sample

Developing Contract-First Microservice is always a good practice. 
Used to facilitate both service owner & consumer services to develop their components in parallel. 
Also, it gives a lot more flexibility to the service owner to handle the versioning of his api. 

## Setup & Implementation

Here is the technological stack we will be using to achieve our goal:

* Spring Boot
* WebFlux
* Maven
* Swagger (Open Api Specification 3 )

### 1 - Write your specifications
Let's start with our swagger file. This file is the service contract of our API. 
This is the most important file in a contract first api implementation since this file will rule all our controllers inputs and outputs.
Here we have a simple GET/pet and POST/pet. As soon as this file is written you can share it to your consumer and start development in parallel.

Here is our simple pet api specification : 

*You can easily edit your spec file in inteliji with live preview or even directly in [swagger editor online](https://editor.swagger.io/)*

```yaml
openapi: '3.0.3'
info:
  version: 1.0.0
  title: Simple Pet Store
  termsOfService: https://swagger.io/
  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html
  contact:
    name: William Jauffret
servers:
  - url: http://localhost
    description: local
  - url: https://dev.store.com
    description: development
  - url: https://stage.store.com
    description: stage

paths:
  /pet:
    get:
      tags:
        - "Pet"
      summary: "Retrieve all Pets"
      description: "Returns a collection of Pet"
      operationId: "getPets"
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Pet'
        "400":
          description: Bad Request
    post:
      tags:
        - "Pet"
      summary: "Add a Pet"
      description: "Add a single Pet"
      operationId: "addPet"
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Pet'
      responses:
        "200":
          description: OK
        "400":
          description: Bad Request

components:
  schemas:
    Pet:
      type: object
      required:
        - petId
      properties:
        petId:
          type: integer
        name:
          type: string
        color:
          type: string
        birthDate:
          type: string
          format: date-time
```

### 2 - Generate your interfaces and dto 

Once you have written your specifications, the next step will be to generate interfaces to implements. 
Doing this will assure us that our api code is strictly tied to our swagger specification. This is contract first. 

To achieve this goal we will be using the [open api maven generator plugin](https://openapi-generator.tech/docs/plugins/). 

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>5.4.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/swaggers/pet-api-swagger.yaml</inputSpec>
                <generatorName>spring</generatorName>
                <packageName>${project.groupId}.springwebfluxcontractfirstsample</packageName>
                <apiPackage>${project.groupId}.springwebfluxcontractfirstsample.api.v1</apiPackage>
                <modelPackage>${project.groupId}.springwebfluxcontractfirstsample.dto</modelPackage>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <modelNameSuffix>Dto</modelNameSuffix>
                <library>spring-boot</library>
                <skipValidateSpec>false</skipValidateSpec>
                <configOptions>
                    <reactive>true</reactive>
                    <delegatePattern>false</delegatePattern>
                    <interfaceOnly>true</interfaceOnly>
                    <dateLibrary>java8</dateLibrary>
                    <java8>true</java8>
                    <sourceFolder>src/main/java</sourceFolder>
                    <!--suppress UnresolvedMavenProperty -->
                    <additionalModelTypeAnnotations>@lombok.Builder @lombok.NoArgsConstructor @lombok.AllArgsConstructor</additionalModelTypeAnnotations>
                    <useTags>true</useTags>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

As you can see here we put the ```reactive``` and the ```interfaceOnly``` option to ```true```.

Now we will launch the ```mvn clean install -DskipTests``` command, this will generate all our dto object and also all endpoints interfaces to implements.

### 3 - Implements generated interfaces

Once you have well generated your interfaces from your specification file, all you have left is to implement it and override methods to add your business code. 

You can also version your api using the ```@RequestMapping``` annotation. 

```java
@RestController
@Validated
@RequestMapping("/api/v1")
public class PetController implements PetApi {

    @Override
    public Mono<ResponseEntity<Flux<PetDto>>> getPets(ServerWebExchange exchange) {
        return Mono.empty();
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> addPet(Mono<PetDto> petDto, ServerWebExchange exchange) {
        return Mono.empty();
    }
}
```

Here we go, now it's all yours. The contract first skeleton is all set up. 
Your api controllers, inputs/outputs, response code, possibles errors and dto will be tied up to your specification.
You can share your swagger file and start development in parallel without worrying about what the front will send or what an 
other service will expect in return. Also others consumers services can be sure that your code respect your specification file.


Happy contract first development, enjoy, bye. 