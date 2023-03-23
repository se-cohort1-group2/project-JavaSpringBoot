insert into concerts (artist, concert_date, tickets_available, ticket_price) values 
('G.E.M.', '2023-03-01 20:30', 90, 288.99),
('MAY DAY', '2023-05-05 19:30', 100, 188.99),
('BLACKPINK', '2023-05-13 19:30', 120, 388.99);

insert into tickets (submission_status, concert_id, user_id, seat_id) values 
(true, 2, 1, 'A1'),
(true, 2, 2, 'A2'),
(true, 3, 2, 'A3');

insert into users (name, phone, email, password, admin_status) values
('Administrator', '+65 8123 4567', 'admin@mail.com', 'admin', true);
insert into users (name, email, password) values
('Phoebe Yong', 'phoebeykq@gmail.com', 'phoebe'),
('Lye Yong Xin', 'lye.yong.xin.9660@gmail.com', 'yongxin'),
('Ong Xin Zhi', 'xong002@gmail.com', 'xinzhi');

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