-- Schema for Taco Cloud application
-- This script creates tables for Ingredients, Tacos, and Taco Orders
create table if not exists Ingredient (
    id varchar(4) not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco (
    id identity primary key,
    name varchar(50) not null,
    createdAt timestamp not null
);

-- Join table for many-to-many relationship between Tacos and Ingredients
-- 关联表（join table）：例如多对多关系的中间表，有时只定义两个外键，但没显式主键。
-- 最佳实践：应该把 两个外键的组合设为 复合主键（composite primary key），这样可以避免重复行
create table if not exists Taco_Ingredients (
    taco bigint not null,
    ingredient varchar(4) not null,
    primary key (taco, ingredient)
);

alter table Taco_Ingredients
    add foreign key (taco) references Taco(id);
alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient(id);

create table if not exists Taco_Order (
    id identity primary key,
    name varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    state varchar(20) not null,
    zip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);

-- Join table for many-to-many relationship between Taco Orders and Tacos
create table if not exists Taco_Order_Tacos (
    tacoOrder bigint not null,
    taco bigint not null,
    primary key (tacoOrder, taco)
);

alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order(id);
alter table Taco_Order_Tacos
    add foreign key (taco) references Taco(id);

-- create tables for user authentication and authorization with JDBC-based user store
create table if not exists Users(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);

create table if not exists UserAuthorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references Users(username)
);

create unique index if not exists ix_auth_username on UserAuthorities (username,authority);
