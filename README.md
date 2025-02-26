# Rewards System
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. 
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 and $100 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). 
Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

## Tech Stack
- Java 17
- Spring Boot
- JPA/Hibernate
- H2
- JUnit Test Cases
- Maven

## Installation & Setup
## Prerequisites
- JDK 17
- Maven
## Steps to Run:
  Clone the Repository:
    git clone https://github.com/RiyaDam/Rewards.git
    cd Rewards
## Build the Project:
    mvn clean install
## Run the Application:
    mvn spring-boot:run
## Access API:
    The application will start on http://localhost:8080

## API Details
Get Reward Points for a Customer
- URL:
  http://localhost:8080/customers/{customerId}/rewards
- Method: GET
- Description: Retrieves reward points for a given customer over the last three months.
- Example Request:
  GET http://localhost:8080/customers/1/rewards
- Response:
  * 200 OK: Returns reward details for the customer.
  {
    "customerId": 1,
    "points": {
      "Jan-2025": 150,
      "Dec-2024": 150,
      "Nov-2024": 0
    },
    "totalRewards": 300
  }
Error Responses
  * 404 Not Found: If the customer ID is invalid/missing.
      Invalid / Missing customer Id: 10

## Running Tests
    mvn test
  
  
