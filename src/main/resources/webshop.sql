drop schema webshop cascade;

Create schema "webshop";

set search_path = "webshop";


CREATE table Categories (
    category_id int primary key,
    category_navn text not null
);

CREATE table Products (
    product_id int primary key,
    name text not null,
    description text not null,
    category_id int,
    price decimal,
    amount int not null
);

CREATE table ProductCategories (
    product_id int,
    foreign key (product_id) references Products(product_id),
    category_id int,
    foreign key (category_id) references Categories(category_id),
    primary key (product_id, category_id)
);

CREATE table Cities (
    postcode int primary key,
    name text not null
);

CREATE table Orders (
    order_id int primary key,
    customer_fullname text not null,
    customer_street text not null,
    customer_postcode int,
    date date not null,
    status boolean,
    total int not null,
    phone_number int not null,

    foreign key (customer_postcode) references Cities(postcode)
);


CREATE table OrderProducts (
    order_id int,
    foreign key (order_id) references Orders(order_id),
    product_id int,
    foreign key (product_id) references Products(product_id),
    primary key (order_id, product_id)
);

CREATE table Admins (
    admin_id int primary key,
    username text not null,
    password text not null,
    email text not null,
    birthdate date not null,
    first_name text not null,
    last_name text not null
);




-- Insert dummy data for Katagori table
INSERT INTO Categories VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Home Decor'),
(4, 'Books'),
(5, 'Sports');

-- Insert dummy data for Vare table
INSERT INTO Products VALUES
(1, 'Smartphone', 'En mobil kommunikations- og computerenhed.', 1, 500, 10),
(2, 'T-Shirt', 'En afslappet, kortærmet beklædningsgenstand ofte lavet af bomuld.', 2, 20, 50),
(3, 'Pudebetræk', 'Et dekorativt stofbetræk til puder.', 3, 15, 30),
(4, 'Novelle', 'En skønlitterær bog til historiefortælling og underholdning.', 4, 10, 20),
(5, 'Fodbold', 'En kugleformet bold brugt i fodboldsporten.', 5, 25, 15),
(6, 'Bærbar', 'En bærbar computer designet til mobilitet.', 1, 800, 5),
(7, 'Jeans', 'En type slidstærke, afslappede bukser, der typisk er lavet af denim.', 2, 30, 40),
(8, 'Bordlampe', 'Et lysarmatur placeret på borde eller skriveborde.', 3, 25, 20),
(9, 'Kogebog', 'En bog med opskrifter og madlavningsvejledning.', 4, 15, 15),
(10, 'Basketball', 'En kugleformet bold brugt i basketballsporten.', 5, 20, 10);


-- Insert dummy data for VareKatagori table
INSERT INTO ProductCategories VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 1),
(7, 2),
(8, 3),
(9, 4),
(10, 5);

-- Insert dummy data for By table
INSERT INTO Cities VALUES
(1000, 'City A'),
(2000, 'City B'),
(3000, 'City C'),
(4000, 'City D'),
(5000, 'City E');

-- Insert dummy data for Ordre table
INSERT INTO Orders VALUES
(1, 'John Doe', 'Street 1', 1000, '2021-01-01', true, 100, 12345678),
(2, 'Jane Smith', 'Street 2', 2000, '2021-02-02', false, 200, 87654321),
(3, 'Alice Johnson', 'Street 3', 3000, '2021-03-03', true, 150, 98765432),
(4, 'Bob Williams', 'Street 4', 4000, '2021-04-04', false, 75, 23456789),
(5, 'Emily Davis', 'Street 5', 5000, '2021-05-05', true, 50, 34567890);

-- Insert dummy data for ordreVare table
INSERT INTO OrderProducts VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insert dummy data for Admin table
INSERT INTO Admins VALUES
(1, 'admin1', 'password1', 'admin1@example.com', '1990-01-01', 'Admin', 'One'),
(2, 'admin2', 'password2', 'admin2@example.com', '1995-02-02', 'Admin', 'Two'),
(3, 'admin3', 'password3', 'admin3@example.com', '1985-03-03', 'Admin', 'Three'),
(4, 'admin4', 'password4', 'admin4@example.com', '1980-04-04', 'Admin', 'Four'),
(5, 'admin5', 'password5', 'admin5@example.com', '1992-05-05', 'Admin', 'Five');
