insert into concerts (artist, concert_date, tickets_available, ticket_price) values 
('G.E.M', '2023-03-01 20:30', 90, 288.99),
('May Day', '2023-05-05 19:30:00', 100, 188.99),
('Blackpink', '2023-05-13 19:30:00', 120, 388.99);

insert into tickets (submission_status, concert_id, user_id, seat_id)
values (true, 1, 1, 'A1');
insert into tickets (submission_status, concert_id, user_id, seat_id)
values (true, 1, 1, 'A2');
insert into tickets (submission_status, concert_id, user_id, seat_id)
values (true, 1, 2, 'A3');

insert into users (name, email, phone) values ('Administrator', 'admin@mail.com', '+65 8123 4567');
insert into users (name, email) values ('Phoebe Yong', 'phoebeykq@gmail.com');
insert into users (name, email) values ('Lye Yong Xin', 'lye.yong.xin.9660@gmail.com');
insert into users (name, email) values ('Ong Xin Zhi', 'xong002@gmail.com');