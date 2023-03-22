insert into concerts (artist, concert_date, tickets_available, ticket_price) values 
('G.E.M', '2023-03-01 20:30', 90, 288.99),
('May Day', '2023-05-05 19:30:00', 100, 188.99),
('Blackpink', '2023-05-13 19:30:00', 120, 388.99);

insert into tickets (submission_status, concert_id, user_id, seat_id) values 
(true, 2, 1, 'A1'),
(true, 2, 2, 'A2'),
(true, 3, 2, 'A3');

insert into users (name, email, phone) values
('Administrator', 'admin@mail.com', '+65 8123 4567');
insert into users (name, email) values
('Phoebe Yong', 'phoebeykq@gmail.com'),
('Lye Yong Xin', 'lye.yong.xin.9660@gmail.com'),
('Ong Xin Zhi', 'xong002@gmail.com');

insert into seats (seat_id, seat_category, venue_hall, ticket_price, concert_type) values
('A1', 'A', 'Hall 1', 299.99, 'concert_type'),
('A2', 'A', 'Hall 1', 299.99, 'concert_type'),
('A3', 'A', 'Hall 1', 299.99, 'concert_type'),
('A4', 'A', 'Hall 1', 299.99, 'concert_type'),
('A5', 'A', 'Hall 1', 299.99, 'concert_type'),
('A6', 'A', 'Hall 1', 299.99, 'concert_type'),
('A7', 'A', 'Hall 1', 299.99, 'concert_type'),
('A8', 'A', 'Hall 1', 299.99, 'concert_type'),
('A9', 'A', 'Hall 1', 299.99, 'concert_type'),
('A10', 'A', 'Hall 1', 299.99, 'concert_type');