insert into roles (name)
values ('ROLE_USER'),
('ROLE_MODERATOR'),
('ROLE_ADMIN');

insert into users (username, password, email, role_id)
values ('user', '$2a$12$O/0bIyf6ztGVZ/dY/9d35.T555FighHj.8/7ZY5rVz8C7S081KLx6', 'user@gmail.com', 1),
 ('moderator', '$2a$12$O/0bIyf6ztGVZ/dY/9d35.T555FighHj.8/7ZY5rVz8C7S081KLx6', 'ololo@gmail.com', 2),
 ('andriesko', '$2a$12$eAB9KFFlQI./J/tB90AT4OzVlXdulX8DPQq592lKln0AnzsbQgKvu', 'andrieskox@gmail.com', 3);




