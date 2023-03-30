create table public.users (
    id int auto_increment,
    name varchar(255),
    phone varchar(255),
    email varchar(255) not null,
    password varchar(255) not null,
    admin_status boolean default false,
    updated_at timestamp not null default current_timestamp,
    created_at timestamp not null default current_timestamp,
    primary key (id)
);

create table public.concerts (
    id int auto_increment,
    artist varchar(255),
    concert_date datetime,
    concert_venue varchar(255),
    tickets_available int,
    ticket_price float,
    updated_at timestamp not null default current_timestamp,
    created_at timestamp not null default current_timestamp,
    primary key (id)
);

create table public.tickets (
    ticket_id int auto_increment,
    concert_id int,
    seat_id varchar(255),
    user_id int,
    submission_status boolean default false,
    created_at timestamp not null default current_timestamp,
    primary key (ticket_id)
);

create table public.seats (
    seat_id varchar(255) not null,
    seat_category varchar(255),
    venue_hall varchar(255),
    ticket_price float,
    concert_type varchar(255),
    primary key (seat_id)
);