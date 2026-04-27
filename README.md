# E-Commerce Backend Application

A backend-focused E-Commerce Application built using Java, Spring Boot, MySQL, JWT, OAuth2, JUnit, and Mockito.  
The project demonstrates product catalog management, user authentication, payment gateway integration, database persistence, API integration, and backend unit testing.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA / Hibernate
- MySQL
- JWT
- OAuth2
- Razorpay Payment Gateway
- FakeStoreAPI
- JUnit
- Mockito
- Maven

## Project Overview

This project solves the backend problem of managing core e-commerce workflows such as:

- Product catalog management
- Category management
- User authentication and authorization
- Payment workflow integration
- Third-party product API consumption
- Persistent data storage using MySQL
- Backend unit testing for reliability

## Key Features

### Product Catalog Service

- Developed REST APIs for managing products and categories.
- Integrated third-party FakeStoreAPI for fetching product and category data.
- Used Spring Data JPA and Hibernate for database interaction.
- Implemented entity relationships between Product and Category.
- Optimized the N+1 query problem using batch fetching.

### User Authentication Service

- Built UserAuthService for secure authentication and authorization.
- Implemented JWT-based authentication.
- Added OAuth2-based login support.
- Used secure authentication flow to protect backend APIs.

### Payment Service

- Integrated Razorpay Payment Gateway for payment workflow handling.
- Used Strategy Design Pattern to keep payment gateway logic flexible and extensible.
- Designed the payment layer so that new gateways can be added with minimal changes.

### Database

- Used MySQL for persistent data storage.
- Leveraged ACID-compliant transaction handling for reliable data consistency.
- Used Base Model inheritance to share common entity attributes across models.

### Testing

- Added backend unit tests using Spring Boot Test, JUnit, and Mockito.
- Covered controller and service-layer logic.
- Achieved around 30% backend code coverage.
- Improved reliability and reduced regression risk during future changes.

## Design Decisions

### Strategy Design Pattern

The Strategy Design Pattern is used in the payment module to avoid tightly coupling the application with a single payment gateway.

For example, Razorpay is implemented as one payment strategy. In the future, other payment providers like Stripe, PayPal, or Paytm can be added without changing the core payment service logic.

### Batch Fetching for N+1 Query Optimization

JPA relationships can cause the N+1 query problem when related entities are fetched repeatedly in separate queries.  
Batch fetching was used to reduce unnecessary database calls and improve query performance.

### Base Model Inheritance

Common fields such as `id`, timestamps, or shared entity attributes are placed in a base model so that multiple entities can reuse them without duplicate code.

## Project Structure

```text
E-Commerce-Application
├── PaymentService
├── UserAuthenticationService
├── src
│   └── main
│       └── java
│           └── com.pm.productcatalogservice
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
