create table public.users (
    id int auto_increment,
    name varchar(100) not null,
    email varchar(255) not null,
    phone varchar(30),
    created_at timestamp not null default current_timestamp,
    primary key (id)
);