DROP TABLE tour IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE authorities IF EXISTS;

CREATE TABLE tour(
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    long_desc VARCHAR(4000)
);
create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);