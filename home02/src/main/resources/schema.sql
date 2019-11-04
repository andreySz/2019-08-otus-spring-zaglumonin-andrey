drop table if exists authors;
create table authors (id bigint primary key auto_increment, name varchar(255));

drop table if exists genres;
create table genres (id bigint primary key auto_increment, name varchar(64));

drop table if exists books;
create table books (id bigint primary key auto_increment, title varchar(128), author_id bigint references authors, genre_id bigint references genres);
