drop table if exists transacao;
drop table if exists conta;
create table conta(
         id SERIAL primary key,
         titular varchar (100) not null,
         saldo numeric (10, 2) default 0.00

);
create table transacao (
     id serial primary key,
     tipo varchar (20) not null,
     valor numeric (10, 2) not null,
     data timestamp not null,
     remetente varchar (100),
     destinatario varchar (100),
     conta_id int references conta(id) on delete cascade
);
