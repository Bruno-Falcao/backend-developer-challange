# Backend challange
> Project Status : Completed :heavy_check_mark:

## Description

- This project is a case for Cayena Backend developer role;
- This project is a Rest API that represents a simple Store;

## Api  endpoints🏁 
- An endpoint to list all product;
- An endpoint to see just one product by 'id';
- An endpoint to insert a new product;
- An endpoint to update a product;
- An endpoint to delete a product;
-An endpoint to increase or decrease the quantity stock

## Table of Contents
- [Usage](#usage)
- [Deployed](#deployed)
- [Installation](#installation)
- [PostmanDocumentation](https://documenter.getpostman.com/view/15800965/2s93kz55PV)

## Usage
- You can use the deployed version
- You can download the project and run locally 

## Deployed
You can go to the [Postman collection](https://documenter.getpostman.com/view/15800965/2s93kz55PV) an use the API.

## Installation

To run this project you need:
- Java 11
- Maven
- MySql local instance

### First of you have to clone the repository

Inside the application.yml you can see some mandatory fields that should be passed as enviroment variables.

You can set all enviroment variables by following these steps:
1. From the main menu, select Run | 
2. Edit Configurations or choose Edit Configurations from the run/debug configurations selector on the toolbar. 
3. In the Run/Debug Configurations dialog, select a configuration you want to add the environment variables to. 
4. Type the variable name and value: CLEARDB_DATABASE_URL="your_local_host";DATABASE_PASS="your_db_pass";DATABASE_USER="your_db_user";DB_NAME=""your_db_name".


### After downloading all project dependencies you can use this [Postman Collection](https://documenter.getpostman.com/view/15800965/2s93kz55PV) to use the API.
  
## Tests
  You can run all tests easyly with intellij
