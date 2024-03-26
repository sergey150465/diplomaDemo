create table client
(
    id int primary key,
    login varchar(255),
    password varchar(255),
    authority varchar(255)
);

insert into client (id, login, password, authority)
values (1, 'log', 'pass', 'USER1'),
       (2, 'log2', 'pass2', 'USER2');

create table client_files
(
    id int primary key,
    file_name varchar(255),
    file_content oid,
    client_id int references client(id)
);

insert into client_files(id, file_name, file_content, client_id)
values (201, '1.png', lo_from_bytea(0, '71,77,65,72,74,79'), 1),
       (202, '2.png', lo_from_bytea(0, '61,73,64,66,67'), 1),
       (203, '3.png', lo_from_bytea(0, '64,66,67,68,6a'), 1);


