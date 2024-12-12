## About
Demo project for Alten.

It is a rest API server, built using spring-boot, using in-memory H2 database.

## How to run project
### Requirements
- Java 17.0
- Maven 3.9.9

### Run
- Start web server using maven and spring boot plugin: `$ mvn spring-boot:run`.
- Once startup completes, web server should be up and running on port `8080`.

### Available APIs

### Note: Endpoints don't work right now due to in progress auth/aut implementation - use a previous commit.

#### Category
- Create: `curl -X POST -H "Content-Type: application/json" -d '{"name": "Garden"}' localhost:8080/category`.
- Get (all): `curl localhost:8080/category`.
- Get (by name): `curl localhost:8080/category?name=gar`.

#### Product
- Create: `curl -X POST -H "Content-Type: application/json" -d '{"name": "Shovel", "basePrice": 100, "categoryId": 1}' localhost:8080/product`.
- Delete: `curl -X DELETE localhost:8080/product/Chips`.
- Get (all): `curl localhost:8080/product`.
- Get (by category): `curl localhost:8080/product?categoryId=1`.
