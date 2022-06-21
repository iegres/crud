-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- create table authors (
-- 	id uuid not null default uuid_generate_v4() primary key,
-- 	author_name varchar (255) not null,
-- 	created_date timestamp not null default now(),
-- 	last_updated timestamp not null default now()
-- );

-- insert into authors (author_name, created_date, last_updated) values ('author_0', now(), now());
-- insert into authors (author_name, created_date, last_updated) values ('author_1', now(), now());

CREATE TABLE book
  (
     id         UUID NOT NULL,
     NAME       VARCHAR(255),
     author_id  UUID,
     PRIMARY KEY (id)
  );

CREATE TABLE author
  (
     id         UUID NOT NULL,
     NAME       VARCHAR(255),
     books      INT DEFAULT 0,
     PRIMARY KEY (id)
  );

ALTER TABLE book
  ADD CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author;
