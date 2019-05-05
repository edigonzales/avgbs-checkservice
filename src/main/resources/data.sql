create table if not exists persistent_logins ( 
     username varchar(100) not null, 
     series varchar(64) primary key, 
     token varchar(64) not null, 
     last_used timestamp not null
);

delete from  user_role;
delete from  roles;
delete from  users;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ACTUATOR'),
(3, 'ROLE_USER');

-- Password: ziegler12
INSERT INTO users (id, email, password, name) VALUES 
(1, 'admin@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'Admin'),
(3, 'user@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User');

insert into user_role(user_id, role_id) values
(1,1),
(1,2),
(1,3),
(3,2);
