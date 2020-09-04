# Customer Detail Management Service

This Application is Microservice for managing Customer Details

## About

This application manages customer details with their address. Search by first or/and last name feature enabled. This application can add customer having only one living address. The address can be modified. This application is build on Spring boot 2 and enabled with swagger. Below Technological stack is used.

- Java 11
- Spring Boot 2
- Swagger 2


## Operations
 - POST : /customer/create - For adding Customer detail
 - PUT : /customer/cust-id - For adding Customer detail
 - GET : /customer/all - For fetching details of all Customer
 - GET : /customer/cust-id/{cust-id} - For fetching customer detail by customer id
 - GET : /customer/searchbyname - Search by first name or last name or with both first and last name

## Sample data 

Swagger is enabled. After installing the please click [Here](http://localhost:8080/swagger-ui.html#/) and make use of below data for create and update

 Request Body  for Create :  

```json

 {
    "first_name":"Brian",
    "last_name":"Hamphire",
    "age":45,
    "cust_address":{
        "door_no":"1",
        "street":"Bhoi Street",
        "city":"Jeypore",
        "country":"India",
        "zip_code":"764001"
    }
}
```
 Request Body  for Update :  

```json
 {
 	 "cust_id":"1",
    "first_name":"Brian",
    "last_name":"Hamphire",
    "age":45,
    "cust_address":{
        "door_no":"237-1",
        "street":"Briardale St",
        "city":"New York",
        "country":"United States",
        "zip_code":"560059"
    }
}
```
## Production Enablement

The spring boot 2 is enabled with actuator to monitor the health of the application. Also, profiling has been done for auto deployment to in Dev, QA, Staging and Production environment. Extensive test cases have been incorporated.

## Installation
The application is build with Spring Boot 2 and self started with contained tomcat. Please build and run the customer-detail-management-0.0.1-SNAPSHOT.jar. Use below command to start locally 

```bash
java -jar target/customer-detail-management-0.0.1-SNAPSHOT.jar
```
## Contributor
- Rukmini Krishna Achary Mullapudi - mrkachary@gmail.com
