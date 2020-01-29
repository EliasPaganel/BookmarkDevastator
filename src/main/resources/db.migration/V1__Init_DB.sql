create sequence hibernate_sequence start 1 increment 1;

create table bookmark (
    id int8 not null,
    file_name varchar(255),
    link varchar(255),
    text varchar(255),
    user_id int8, primary key (id));

create table user_role (
    user_id int8 not null,
    roles varchar(255));

create table usr (
    id int8 not null,
    activation_code varchar(255),
    active boolean,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id));

alter table if exists bookmark
    add constraint FK3claygmi8gds4yd97ofdtrtnu
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5
    foreign key (user_id) references usr;