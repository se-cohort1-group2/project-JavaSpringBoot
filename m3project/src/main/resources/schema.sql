create table public.concerts (
    id int auto_increment,
    artist varchar(255),
    concert_date datetime,  
    tickets_available int,
    ticket_price float, 
    created_at timestamp not null default current_timestamp,
    primary key (id)
);

create table public.tickets (
    id int auto_increment,
    submission_status boolean default false,
    concert_id int,
    user_id int,
    seat_id varchar(255),
    created_at timestamp not null default current_timestamp,
    primary key (id)
);

create table public.users (
    id int auto_increment,
    name varchar(100) not null,
    email varchar(255) not null,
    phone varchar(30),
    created_at timestamp not null default current_timestamp,
    primary key (id)
);