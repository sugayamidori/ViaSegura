create table usuario(
    id uuid not null primary key,
    nome varchar(20) not null,
    senha varchar(300) not null,
	email varchar(150) not null unique,
    roles varchar[]
);