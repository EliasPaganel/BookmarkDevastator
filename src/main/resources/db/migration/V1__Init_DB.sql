create sequence hibernate_sequence start 1 increment 1;

create table bookmark (
    id int8 not null,
    file_name varchar(255),
    link varchar(255),
    text varchar(2048) not null,
    user_id int8, primary key (id));

create table user_role (
    user_id int8 not null,
    roles varchar(255));

create table usr (
    id int8 not null,
    activation_code varchar(255),
    active boolean,
    email varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id));

alter table if exists bookmark
    add constraint bookmark_user_fk
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;