# WebShop API
A REST API written in Java serving as the backend for a webshop. It utilizes RabbitMQ for messaging between different components and PostgreSQL for data persistence

## Setup
Clone the repo
```bash
git clone https://github.com/Altinett/SEP3-WebShop-API.git your-project-name
```
Open IntelliJ IDE and for each of the `pom.xml` files inside `Persistence`, `RestAPI` and `Shared`, right click and press `Add as Maven Project`

## API Endpoints

### `GET /orders`
Retrieve a list of all orders

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

### `GET /orders/{orderId}`
Retrieve details of a specific order by its ID

```python
url = "http://localhost:8080/orders/1"

response = requests.get(url)
```
```json
{
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
}
```

### `POST /orders/order`
Place a new order

```python
url = "http://localhost:8080/orders/order"

data = {
	"firstname": "Bob",
	"lastname": "Bobbington",
	"address": "Bobtown 24",
	"postcode": 1000,
	"status": False,
	"phoneNumber": 12121212,
	"email": "bob@bob.bob",
	"productIds": [1, 2, 3, 4, 5]
}

response = requests.post(url, json = data)
```
```json
{
    "orderId": 7,
    "firstname": "Bob",
    "lastname": "Bobbington",
    "address": "Bobtown 24",
    "postcode": 1000,
    "date": 1699721590735,
    "status": false,
    "total": 0,
    "phoneNumber": 12345678,
    "email": "bob@gmail.com",
    "productIds": [1, 2, 3, 4, 5]
}
```