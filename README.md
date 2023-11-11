# WebShop API

## Overview
A REST API written in Java serving as the backend for a webshop. It utilizes RabbitMQ for messaging between different components and PostgreSQL for data persistence

## Setup
Clone the repo
```bash
git clone https://github.com/Altinett/SEP3-WebShop-API.git your-project-name
```
Open IntelliJ IDE and for each of the `pom.xml` files inside `Persistence`, `RestAPI` and `Shared`, right click and press `Add as Maven Project`

## API Endpoints
### Orders
#### Retrieve a list of all orders
`GET /orders`

```python
url = "http://localhost:8080/orders"

response = requests.get(url)
```
```json
[{
    "orderId": 1,
    "firstname": "John",
    "lastname": "Doe",
    "address": "Street 1",
    "postcode": 1000,
    "date": 1699720881264,
    "status": true,
    "total": 100,
    "phoneNumber": 12345678,
    "email": "johndoe@gmail.com",
    "productIds": [1]
}, {
    "orderId": 2,
    "firstname": "Jane",
    "lastname": "Smith",
    "address": "Street 2",
    "postcode": 2000,
    "date": 1699720881264,
    "status": false,
    "total": 200,
    "phoneNumber": 87654321,
    "email": "janesmith@gmail.com",
    "productIds": [2]
}]
```
