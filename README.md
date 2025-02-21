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

## API Details
- URL:
  http://localhost:8080/customers/{customerId}/rewards
- Method: GET
- Response:
  * 200 OK: Returns reward details for the customer.
  * 404 Not Found: If the customer ID is ivalid/missing.
