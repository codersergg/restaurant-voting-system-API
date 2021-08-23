INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user1@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('admin@codersergg.com', 'Admin_First', 'Admin_Last', '{noop}admin'),
       ('user2@gmail.com', 'User_2', 'User_2_Last', '{noop}password'),
       ('user3@gmail.com', 'User_3', 'User_3_Last', '{noop}password');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO RESTAURANT (NAME, RATING)
VALUES ('A1', 0),
       ('B2', 0),
       ('Сытый енот', 0);

INSERT INTO DISH (NAME, PRISE, CREATED, RESTAURANT_ID)
VALUES ('Блюдо1', 100, '2021-08-09', 1),
       ('Блюдо2', 200, '2021-08-09', 1),
       ('Блюдо3', 300, '2021-08-09', 1),
       ('Блюдо1', 150, '2021-08-09', 2),
       ('Блюдо2', 250, '2021-08-09', 2),
       ('Блюдо3', 350, '2021-08-09', 2),
       ('Блюдо1', 190, '2021-08-09', 3),
       ('Блюдо2', 290, '2021-08-09', 3),
       ('Блюдо3', 390, '2021-08-09', 3),
       ('Сегодняшнее Блюдо1', 1000, now(), 1),
       ('Сегодняшнее Блюдо2', 2000, now(), 1),
       ('Сегодняшнее Блюдо3', 3000, now(), 1),
       ('Сегодняшнее Блюдо1', 1500, now(), 2),
       ('Сегодняшнее Блюдо2', 2500, now(), 2),
       ('Сегодняшнее Блюдо3', 3500, now(), 2),
       ('Сегодняшнее Блюдо1', 1900, now(), 3),
       ('Сегодняшнее Блюдо2', 2900, now(), 3),
       ('Сегодняшнее Блюдо3', 3900, now(), 3);

INSERT INTO VOTE (DATE, TIME, USER_ID, RESTAURANT_ID)
VALUES ('2021-08-09', '10:05', 1, 1),
       ('2021-08-09', '10:27', 3, 3),
       ('2021-08-09', '10:35', 4, 1),
       ('2021-08-10', '10:05', 1, 2),
       ('2021-08-10', '10:27', 3, 1),
       ('2021-08-10', '10:35', 4, 2);