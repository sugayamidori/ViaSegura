CREATE TABLE client(
   id uuid not null primary key,
   client_id varchar(150) not null,
   client_secret varchar(400) not null,
   redirect_uri varchar(200) not null,
   scope varchar(50)
);