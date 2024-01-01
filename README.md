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

