CREATE DATABASE IF NOT EXISTS Restaurant;
Use Restaurant;

CREATE TABLE IF NOT EXISTS Admin(
id int auto_increment primary key,
username varchar(100),
password varchar(100)
);

create table Categories(
id int auto_increment primary key,
product_id varchar(100),
product_name varchar(100),
type varchar(100),
price double,
status varchar(100)
);
create table product(
id int auto_increment primary key not null,
product_id varchar(100) not null,
product_name varchar(100) not null,
type varchar(100) not null,
price double not null,
quantity int(100) not null,
date date null,
customer_id int(100) not null
);


create table product_info(
id int auto_increment primary key not null,
customer_id int(100) not null,
product_id varchar(100) not null,
total double not null,
date date null

);


INSERT INTO Admin(id, username, password) value
(null,'admin', '123456');

SELECT * FROM Categories;
SELECT * FROM product;
SELECT * FROM  product_info;
 
 SHOW TABLES;
SHOW CREATE TABLE Categories;
SHOW CREATE TABLE product;
SHOW CREATE TABLE product_info;
 
 USE Restaurant;
SHOW COLUMNS FROM product;
SHOW COLUMNS FROM product_info;


