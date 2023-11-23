DROP SCHEMA webshop CASCADE;

CREATE SCHEMA "webshop";

SET search_path = "webshop";

CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;


CREATE TABLE Categories (
                            id int primary key,
                            name text not null
);

CREATE TABLE Products (
                          id serial primary key,
                          name text not null,
                          description text not null,
                          price decimal not null,
                          amount int not null,
                          flagged bool not null,
                          image text not null
);

CREATE TABLE ProductCategories (
                                   product_id int,
                                   foreign key (product_id) references Products(id) on delete cascade,
                                   category_id int,
                                   foreign key (category_id) references Categories(id),
                                   primary key (product_id, category_id)
);

CREATE TABLE Cities (
                        postcode int primary key,
                        name text not null
);

CREATE TABLE Orders (
                        id serial primary key,
                        firstname text not null,
                        lastname text not null,
                        address text not null,
                        postcode int not null,
                        date timestamp not null,
                        status boolean not null,
                        total int not null,
                        phonenumber int not null,
                        email text not null,

                        foreign key (postcode) references Cities(postcode)
);


CREATE TABLE OrderProducts (
                               order_id int,
                               foreign key (order_id) references Orders(id),
                               product_id int,
                               quantity int,
                               foreign key (product_id) references Products(id) on delete cascade,
                               primary key (order_id, product_id)
);

CREATE TABLE Admins (
                        id int primary key,
                        username text not null,
                        password text not null,
                        email text not null,
                        birthdate date not null,
                        firstname text not null,
                        lastname text not null
);



-- Insert dummy data for Katagori table
INSERT INTO Categories
VALUES (1, 'Electronics'),
       (2, 'Clothing'),
       (3, 'Home Decor'),
       (4, 'Books'),
       (5, 'Sports');

-- Insert dummy data for Vare table
INSERT INTO Products (id, name, description, price, amount, flagged)
VALUES (default, 'Smartphone', 'En mobil kommunikations- og computerenhed.', 500, 10, false),
       (default, 'T-Shirt', 'En afslappet, kortærmet beklædningsgenstand ofte lavet af bomuld.', 20, 50, false),
       (default, 'Pudebetræk', 'Et dekorativt stofbetræk til puder.', 15, 30, true),
       (default, 'Novelle', 'En skønlitterær bog til historiefortælling og underholdning.', 10, 20, false),
       (default, 'Fodbold', 'En kugleformet bold brugt i fodboldsporten.', 25, 15, false),
       (default, 'Bærbar', 'En bærbar computer designet til mobilitet.', 800, 5, false),
       (default, 'Jeans', 'En type slidstærke, afslappede bukser, der typisk er lavet af denim.', 30, 40, true),
       (default, 'Bordlampe', 'Et lysarmatur placeret på borde eller skriveborde.', 25, 20, false),
       (default, 'Kogebog', 'En bog med opskrifter og madlavningsvejledning.', 15, 15, false),
       (default, 'Basketball', 'En kugleformet bold brugt i basketballsporten.', 20, 10, false);


-- Insert dummy data for VareKatagori table
INSERT INTO ProductCategories
VALUES (1, 1),
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
INSERT INTO Cities
VALUES (1000, 'City A'),
       (2000, 'City B'),
       (3000, 'City C'),
       (4000, 'City D'),
       (5000, 'City E');

-- Insert dummy data for Ordre table
INSERT INTO Orders
VALUES (default, 'John', 'Doe', 'Street 1', 1000, '2021-01-01', true, 100, 12345678, 'johndoe@gmail.com'),
       (default, 'Jane', 'Smith', 'Street 2', 2000, '2021-02-02', false, 200, 87654321, 'janesmith@gmail.com'),
       (default, 'Alice', 'Johnson', 'Street 3', 3000, '2021-03-03', true, 150, 98765432, 'alicejohnson@gmail.com'),
       (default, 'Bob', 'Williams', 'Street 4', 4000, '2021-04-04', false, 75, 23456789, 'bobwilliams@gmail.com'),
       (default, 'Emily', 'Davis', 'Street 5', 5000, '2021-05-05', true, 50, 34567890, 'emilydavis@gmail.com');

-- Insert dummy data for ordreVare table
INSERT INTO OrderProducts
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 2),
       (4, 3, 2),
       (4, 4, 2),
       (5, 5, 4);

-- Insert dummy data for Admin table
INSERT INTO Admins
VALUES (1, 'admin1', 'password1', 'admin1@example.com', '1990-01-01', 'Admin', 'One'),
       (2, 'admin2', 'password2', 'admin2@example.com', '1995-02-02', 'Admin', 'Two'),
       (3, 'admin3', 'password3', 'admin3@example.com', '1985-03-03', 'Admin', 'Three'),
       (4, 'admin4', 'password4', 'admin4@example.com', '1980-04-04', 'Admin', 'Four'),
       (5, 'admin5', 'password5', 'admin5@example.com', '1992-05-05', 'Admin', 'Five');


