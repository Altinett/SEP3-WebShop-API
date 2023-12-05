# WebShop API
A REST API written in Java serving as the backend for a webshop. It utilizes RabbitMQ for messaging between different components and PostgreSQL for data persistence

## Setup
Clone the repo
```bash
git clone https://github.com/Altinett/SEP3-WebShop-API.git your-project-name
```
Open IntelliJ IDE and for each of the `pom.xml` files inside `Persistence`, `RestAPI` and `Shared`, right click and press `Add as Maven Project`

# API Endpoints
## Location
### `GET /location/cities`
Retrieve a list of cities
```python
url = "http://localhost:8080/location/cities"

response = requests.get(url)
```
```json
[{
	"name": "Frederiksberg",
	"zipcode": 2000
},{
	"name": "København Ø",
	"zipcode": 2100
}]
```

## Categories
### `GET /categories`
Retrieve a list of categories
```python
url = "http://localhost:8080/categories"

response = requests.get(url)
```
```json
[{
	"id": 1,
	"name": "Electronics"
},{
	"id": 2,
	"name": "Clothing"
}]
```
### `GET /categories/{categoryId}`
Retrieve a specific category by its id
```python
url = "http://localhost:8080/categories/1

response = requests.get(url)
```
```json
{
	"id": 1,
	"name": "Electronics"
}
```

### `POST /categories/edit`
Edit a category
```python
url = "http://localhost:8080/categories/edit"

data = {
	"id": 1,
	"name": "Gaming"
}

response = requests.post(url, json = data)
```
```json
{
	"id": 1,
	"name": "Gaming"
}
```

## Users
### `GET /users`
Retrieve a user by its username and password
```python
url = "http://localhost:8080/users"

data = {
	"username": "admin",
	"password": "admin"
}

response = requests.get(url, json = data)
```
```json
{
	"id": 1,
	"username": "admin",
	"password": "admin",
	"email": "admin@example.com",
	"firstname": "Jane",
	"lastname": "Smith",
	"dob": 631148400000
}
```
###

## Products
### `GET /products`
Retrieve a list of products

#### Flagged Parameter
`showFlagged` (boolean): include or exclude flagged products. Default value is true. Example: `/products?showFlagged=true`

#### Categories Parameter
`categories` (string): filter products by category ids. Multiple categories can be provided. Default value is none. Example: `/products?categories=1,2,3`

#### Query Parameter
`query` (string): filter products based on a search query. Most matching results will be first within the list. Default value is none. Example: `/products?query=book`

#### Price Range Parameter
`min` (integer): filter products with greater than or equal to the specified value. Default value is none. Example: `/products?min=20`<br>
`max` (integer): filter products with less than or equal to the specified value. Default value is none. Example: `/products?max=800`

#### Pagination Parameters
`pageSize` (integer): number of products to be included for each page. Default value is 15. Example: `/products?pageSize=25`<br>
`page` (integer): page number. Default value is 1. Example: `/products?page=2`

```python
url = "http://localhost:8080/products?showFlagged=true&categories=1,2,3&query=Bo&min=20&max=400"

response = requests.get(url)
```
```json
[{
	"id": 8,
	"amount": 20,
	"name": "Bordlampe",
	"description": "Et lysarmatur placeret på borde eller skriveborde.",
	"price": 25,
	"image": "",
	"categoryIds": [
		3
	],
	"flagged": false
},{
	"id": 2,
	"amount": 46,
	"name": "T-Shirt",
	"description": "En afslappet, kortærmet beklædningsgenstand ofte lavet af bomuld.",
	"price": 20,
	"image": "",
	"categoryIds": [
		2
	],
	"flagged": false
},{
	"id": 7,
	"amount": 40,
	"name": "Jeans",
	"description": "En type slidstærke, afslappede bukser, der typisk er lavet af denim.",
	"price": 30,
	"image": "",
	"categoryIds": [
		2
	],
	"flagged": true
}]
```

## Orders
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
