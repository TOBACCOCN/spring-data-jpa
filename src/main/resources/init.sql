create table user
(
    id bigint not null primary key,
    address varchar(255) null,
    age int not null,
    name varchar(255) null
);


create index idx_address on user (address);


create table user1
(
    address varchar(255) not null,
    name varchar(255) not null,
    age int not null,
    gender varchar(255) null,
    primary key (address, name)
);


create table create_update
(
    id bigint auto_increment primary key,
    create_time datetime(6) null,
    creater varchar(255) null,
    update_time datetime(6)  null,
    updater varchar(255) null
);


create
    definer = root@localhost procedure plus1inout(IN arg int, OUT res int)
begin
    select (arg + 1) into res;
end;