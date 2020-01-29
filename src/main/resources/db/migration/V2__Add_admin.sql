insert into usr(id, username, password, active, email)
values (1, 'admin', '123', true, 'lass710@gmail.com');

insert into user_role(user_id, roles)
values (1, 'USER'), (1, 'ADMIN');