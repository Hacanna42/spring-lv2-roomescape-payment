-- reservation_time
INSERT INTO reservation_time (start_at)
VALUES ('09:00'),
       ('10:00'),
       ('11:00'),
       ('12:00'),
       ('13:00'),
       ('14:00'),
       ('15:00'),
       ('16:00'),
       ('17:00'),
       ('18:00');

-- theme
INSERT INTO theme (name, description, thumbnail)
VALUES ('Mystery Room', 'Solve the mysterious case in 60 minutes',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Prison Break', 'Escape from prison before time runs out',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Zombie Lab', 'Find the cure before zombies take over',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Space Mission', 'Repair the shuttle and return safely',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Haunted House', 'Survive the ghosts and escape',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Spy Academy', 'Pass the spy training missions',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Bank Heist', 'Steal the gold without triggering alarms',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Time Travel', 'Fix the time machine to return',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Alien Invasion', 'Evacuate Earth and contact allies',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg'),
       ('Secret Society', 'Uncover the cult and stop the ritual',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Arth_tapestry2.jpg/500px-Arth_tapestry2.jpg');

-- member
INSERT INTO member (email, password, name, role)
VALUES ('user1@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Alice', 'MEMBER'),
       ('user2@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Bob', 'MEMBER'),
       ('user3@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Charlie', 'MEMBER'),
       ('user4@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'David', 'MEMBER'),
       ('user5@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Eve', 'MEMBER'),
       ('user6@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Frank', 'MEMBER'),
       ('user7@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Grace', 'MEMBER'),
       ('user8@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Hannah', 'MEMBER'),
       ('user9@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Isaac', 'MEMBER'),
       ('user10@example.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', 'Jane', 'MEMBER');

-- reservation (조합 생성)
INSERT INTO reservation (date, member_id, reservation_time_id, theme_id, reservation_status)
VALUES
    ('2025-05-21', 1, 1, 1, 'PENDING'),
    ('2025-05-21', 2, 2, 2, 'PENDING'),
    ('2025-05-21', 3, 3, 3, 'PENDING'),
    ('2025-05-21', 4, 4, 4, 'PENDING'),
    ('2025-05-21', 5, 5, 5, 'PENDING'),
    ('2025-05-21', 6, 6, 6, 'PENDING'),
    ('2025-05-21', 7, 7, 7, 'PENDING'),
    ('2025-05-21', 8, 8, 8, 'PENDING'),
    ('2025-05-21', 9, 9, 9, 'PENDING'),
    ('2025-05-21', 10, 10, 10, 'PENDING'),
    ('2025-05-22', 1, 1, 2, 'PENDING'),
    ('2025-05-22', 1, 1, 1, 'PENDING'),
    ('2025-05-22', 2, 1, 1, 'WAITING'),
    ('2025-05-22', 2, 2, 3, 'PENDING'),
    ('2025-05-22', 3, 3, 4, 'PENDING'),
    ('2025-05-22', 4, 4, 5, 'PENDING'),
    ('2025-05-22', 5, 5, 6, 'PENDING'),
    ('2025-05-22', 6, 6, 7, 'PENDING'),
    ('2025-05-22', 7, 7, 8, 'PENDING'),
    ('2025-05-22', 8, 8, 9, 'PENDING'),
    ('2025-05-22', 9, 9, 10, 'PENDING'),
    ('2025-05-22', 10, 10, 1, 'PENDING'),
    ('2025-05-23', 1, 1, 3, 'PENDING'),
    ('2025-05-23', 2, 2, 4, 'PENDING'),
    ('2025-05-23', 3, 3, 5, 'PENDING'),
    ('2025-05-23', 4, 4, 6, 'PENDING'),
    ('2025-05-23', 5, 5, 7, 'PENDING'),
    ('2025-05-23', 6, 6, 8, 'PENDING'),
    ('2025-05-23', 7, 7, 9, 'PENDING'),
    ('2025-05-23', 8, 8, 10, 'PENDING'),
    ('2025-05-23', 9, 9, 1, 'PENDING'),
    ('2025-05-23', 10, 10, 2, 'PENDING'),
    ('2025-05-24', 1, 1, 4, 'PENDING'),
    ('2025-05-24', 2, 2, 5, 'PENDING'),
    ('2025-05-24', 3, 3, 6, 'PENDING'),
    ('2025-05-24', 4, 4, 7, 'PENDING'),
    ('2025-05-24', 5, 5, 8, 'PENDING'),
    ('2025-05-24', 6, 6, 9, 'PENDING'),
    ('2025-05-24', 7, 7, 10, 'PENDING'),
    ('2025-05-24', 8, 8, 1, 'PENDING'),
    ('2025-05-24', 9, 9, 2, 'PENDING'),
    ('2025-05-24', 10, 10, 3, 'PENDING'),
    ('2025-05-25', 1, 1, 5, 'PENDING'),
    ('2025-05-25', 2, 2, 6, 'PENDING'),
    ('2025-05-25', 3, 3, 7, 'PENDING'),
    ('2025-05-25', 4, 4, 8, 'PENDING'),
    ('2025-05-25', 5, 5, 9, 'PENDING'),
    ('2025-05-25', 6, 6, 10, 'PENDING'),
    ('2025-05-25', 7, 7, 1, 'PENDING'),
    ('2025-05-25', 8, 8, 2, 'PENDING'),
    ('2025-05-25', 9, 9, 3, 'PENDING'),
    ('2025-05-25', 10, 10, 4, 'PENDING'),
    ('2025-05-26', 1, 1, 6, 'PENDING'),
    ('2025-05-26', 2, 2, 7, 'PENDING'),
    ('2025-05-26', 3, 3, 8, 'PENDING'),
    ('2025-05-26', 4, 4, 9, 'PENDING'),
    ('2025-05-26', 5, 5, 10, 'PENDING'),
    ('2025-05-26', 6, 6, 1, 'PENDING'),
    ('2025-05-26', 7, 7, 2, 'PENDING'),
    ('2025-05-26', 8, 8, 3, 'PENDING'),
    ('2025-05-26', 9, 9, 4, 'PENDING'),
    ('2025-05-26', 10, 10, 5, 'PENDING'),
    ('2025-05-27', 1, 1, 7, 'PENDING'),
    ('2025-05-27', 2, 2, 8, 'PENDING'),
    ('2025-05-27', 3, 3, 9, 'PENDING'),
    ('2025-05-27', 4, 4, 10, 'PENDING'),
    ('2025-05-27', 5, 5, 1, 'PENDING'),
    ('2025-05-27', 6, 6, 2, 'PENDING'),
    ('2025-05-27', 7, 7, 3, 'PENDING'),
    ('2025-05-27', 8, 8, 4, 'PENDING'),
    ('2025-05-27', 9, 9, 5, 'PENDING'),
    ('2025-05-27', 10, 10, 6, 'PENDING'),
    ('2025-05-28', 1, 1, 8, 'PENDING'),
    ('2025-05-28', 2, 2, 9, 'PENDING'),
    ('2025-05-28', 3, 3, 10, 'PENDING'),
    ('2025-05-28', 4, 4, 1, 'PENDING'),
    ('2025-05-28', 5, 5, 2, 'PENDING'),
    ('2025-05-28', 6, 6, 3, 'PENDING'),
    ('2025-05-28', 7, 7, 4, 'PENDING'),
    ('2025-05-28', 8, 8, 5, 'PENDING'),
    ('2025-05-28', 9, 9, 6, 'PENDING'),
    ('2025-05-28', 10, 10, 7, 'PENDING'),
    ('2025-05-29', 1, 1, 9, 'PENDING'),
    ('2025-05-29', 2, 2, 10, 'PENDING'),
    ('2025-05-29', 3, 3, 1, 'PENDING'),
    ('2025-05-29', 4, 4, 2, 'PENDING'),
    ('2025-05-29', 5, 5, 3, 'PENDING'),
    ('2025-05-29', 6, 6, 4, 'PENDING'),
    ('2025-05-29', 7, 7, 5, 'PENDING'),
    ('2025-05-29', 8, 8, 6, 'PENDING'),
    ('2025-05-29', 9, 9, 7, 'PENDING'),
    ('2025-05-29', 10, 10, 8, 'PENDING'),
    ('2025-05-30', 1, 1, 10, 'PENDING'),
    ('2025-05-30', 2, 2, 1, 'PENDING'),
    ('2025-05-30', 3, 3, 2, 'PENDING'),
    ('2025-05-30', 4, 4, 3, 'PENDING'),
    ('2025-05-30', 5, 5, 4, 'PENDING'),
    ('2025-05-30', 6, 6, 5, 'PENDING'),
    ('2025-05-30', 7, 7, 6, 'PENDING'),
    ('2025-05-30', 8, 8, 7, 'PENDING'),
    ('2025-05-30', 9, 9, 8, 'PENDING'),
    ('2025-05-30', 10, 10, 9, 'PENDING'),
    ('2025-05-31', 1, 1, 1, 'PENDING'),
    ('2025-05-31', 2, 2, 2, 'PENDING'),
    ('2025-05-31', 3, 3, 3, 'PENDING'),
    ('2025-05-31', 4, 4, 4, 'PENDING'),
    ('2025-05-31', 5, 5, 5, 'PENDING'),
    ('2025-05-31', 6, 6, 6, 'PENDING'),
    ('2025-05-31', 7, 7, 7, 'PENDING'),
    ('2025-05-31', 8, 8, 8, 'PENDING'),
    ('2025-05-31', 9, 9, 9, 'PENDING'),
    ('2025-05-31', 10, 10, 10, 'PENDING'),
    ('2025-06-01', 1, 1, 2, 'PENDING'),
    ('2025-06-01', 2, 2, 3, 'PENDING'),
    ('2025-06-01', 3, 3, 4, 'PENDING'),
    ('2025-06-01', 4, 4, 5, 'PENDING'),
    ('2025-06-01', 5, 5, 6, 'PENDING'),
    ('2025-06-01', 6, 6, 7, 'PENDING'),
    ('2025-06-01', 7, 7, 8, 'PENDING'),
    ('2025-06-01', 8, 8, 9, 'PENDING'),
    ('2025-06-01', 9, 9, 10, 'PENDING'),
    ('2025-06-01', 10, 10, 1, 'PENDING');


INSERT INTO member(email, password, name, role)
VALUES ('asd@naver.com', '6n0Wg7pfyML3/IDomo6lzX2nVdoMLuZf2uBAEkohFmI=:rlzmr6mbkuw/ONyqfgu+yw==', '어드민', 'ADMIN');
