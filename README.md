# Taxi Project

This is the documentation of the project for Enterprise Development Experience.
The idea behind my project is a fictional taxi company in which customers, taxis and trips will be managed in three different microservices.  

Here is a little more information about the microservices, but they will be explained in more detail when their functions are shown.
* Customer-Service: Manage all the information regarding customers.
* Taxi-Service: Manage all information regarding the taxis (cars) that the company has.
* Trip-Service: Combine customer and taxi values to create a trip alongside extra trip information.
* API-Gateway: A service used to brigde between user and the program, allows easy access to a single main url.

## Project schema in Draw.io

This is the schema of the project structure. The first thing the user will come in contact with is the API-Gateway, this is to access all available endpoint. Most endpoints are secured using the Google cloud Platform with OAuth 2.0. 
The gateway can then call the necessary services to show or process the requested data. Each service has a volume for data storage attached. Trip and Taxi use MySQL and Customer is using MongoDB.

![EDE](https://github.com/SeppeVE/EDE/assets/91118345/a50a6e84-1573-48bb-90e3-f7fade2edb0a)

## Endpoints

In order to use the services online, i have hosted them using Okteto. The endpoints that are accesible are:
* GET customers
* POST customer
* PUT customer
* DELETE customer
* GET taxis
* POST taxi
* GET trips

Out of all these endpoints, the only one accesible withouth authorization is GET customers.

## Endpoints showcase

### Get Customers
![Get_customers](https://github.com/SeppeVE/EDE/assets/91118345/acd7f1b5-6262-4bfc-b73e-226f8ab7d2a2)

### Post Customer
![Post_customer_1](https://github.com/SeppeVE/EDE/assets/91118345/f330c4ad-596e-45c0-88f5-3ab024bd4790)
![Post_customer_2](https://github.com/SeppeVE/EDE/assets/91118345/57de8aed-6fda-429b-ac18-4314a53e1a8b)

### Put Customer
![Put_customer_1](https://github.com/SeppeVE/EDE/assets/91118345/1e786298-8fe5-4104-9535-6c0d60754797)
![Put_customer_2](https://github.com/SeppeVE/EDE/assets/91118345/1af572e5-c81f-40cc-b401-92f68d67f008)

### Delete Customer
![Delete_customer_1](https://github.com/SeppeVE/EDE/assets/91118345/6bcee1f6-ce22-4d4b-ba84-d3a924b89485)
![Delete_customer_2](https://github.com/SeppeVE/EDE/assets/91118345/54888ff9-02f5-4ebc-b72e-31156146174f)
Note: In this case it is /delete/customer, for some reason that I haven't been able to find it only works like this and not /customer/delete.

### Get Taxis
![Get_Taxis](https://github.com/SeppeVE/EDE/assets/91118345/544ea6b1-26dc-4a7b-96e8-d3b5a16a40bf)

### Post Taxi
![Post_taxi_1](https://github.com/SeppeVE/EDE/assets/91118345/571ef248-19a1-4eb1-b449-b3034fb88507)
![Post_taxi_2](https://github.com/SeppeVE/EDE/assets/91118345/9bd3283f-b213-41cc-aaca-688817d3be31)

### Get Trips
![Get_Trips](https://github.com/SeppeVE/EDE/assets/91118345/1861b25f-dc63-48eb-91ce-f2bdd05b24be)

## Hosting

In order to access all of this over the internet it has to be hosted of course. I have done this using Okteto. This way my Github repo gets published automatically and quite easy as well. To achieve this, the services are made into Docker images and containers which are then managed by Okteto.  

* [Get Customers (only one without auth)](https://api-gateway-seppeve.cloud.okteto.net/customers)
* [Post Customer](https://api-gateway-seppeve.cloud.okteto.net/customer/post)
* [Put Customer](https://api-gateway-seppeve.cloud.okteto.net/cutomer/put/id)
* [Delete Customer](https://api-gateway-seppeve.cloud.okteto.net/delete/customer/id)
* [Get Taxis](https://api-gateway-seppeve.cloud.okteto.net/taxis)
* [Post Taxi](https://api-gateway-seppeve.cloud.okteto.net/taxi/post)
* [Get Trips](https://api-gateway-seppeve.cloud.okteto.net/trips)
