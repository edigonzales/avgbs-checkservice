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
(3, 'ROLE_USER'),
(4, 'ROLE_BSBOE'),
(5, 'ROLE_BSBGR'),
(6, 'ROLE_LWAG'),
(7, 'ROLE_SUTTER'),
(8, 'ROLE_EBAG'),
(9, 'ROLE_WHAG');

-- Password: ziegler12
INSERT INTO users (id, email, password, name) VALUES 
(1, 'admin@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'Admin'),
(2, 'user@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User'),
(3, 'user_bsboe@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User BSB Oensingen AG'),
(4, 'user_bsbgr@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User BSB Grenchen AG'),
(5, 'user_lwag@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User Lerch Weber AG'),
(6, 'user_sutter@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User Sutter AG'),
(7, 'user_ebag@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User E+B AG'),
(8, 'user_whag@gmail.com', '$2a$10$/JODsQlKKPmYFjgaXIpccuUrcgZ2sLN2gfxzxq4H4KHRPKzs7ljb.', 'User W+H AG');

insert into user_role(user_id, role_id) values
(1,1),
(1,2),
(1,3),
(2,2),

(3,3),
(4,3),
(5,3),
(6,3),
(7,3),
(8,3),
(3,4),
(4,5),
(5,6),
(6,7),
(7,8),
(8,9);


--CREATE TABLE IF NOT EXISTS identnd (
--    id int primary key,
--    role_id int not null,
--    identnd varchar(12) not null,
--    municipality varchar(200) not null,
--    fosnr int not null,
--    enabled boolean not null,
--    foreign key (role_id) references roles(id)
--);

INSERT INTO identnd(id, role_id, identnd, municipality, fosnr, enabled) values
(1, 4, 'SO0200002401', 'Egerkingen', 2401, true);
