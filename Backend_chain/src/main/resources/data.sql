CREATE TABLE person
(
    chat_id     BIGINT PRIMARY KEY,
    first_name  VARCHAR(20),
    last_name   VARCHAR(40),
    username_tg VARCHAR(124),
    phone       VARCHAR(12)
);

CREATE TABLE image_of_person
(
    id             SERIAL PRIMARY KEY,
    person_chat_id BIGINT REFERENCES person (chat_id) ON DELETE CASCADE NOT NULL,
    file_path      TEXT
);

CREATE TABLE executor
(
    id             SERIAL PRIMARY KEY,
    person_chat_id BIGINT REFERENCES person (chat_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE activity_area
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(124) NOT NULL
);

CREATE TABLE work_experience
(
    id       SERIAL PRIMARY KEY,
    category VARCHAR(20) NOT NULL
);

CREATE TABLE user_status
(
    id       SERIAL PRIMARY KEY,
    category VARCHAR(60) NOT NULL
);

CREATE TABLE resume
(
    id                   SERIAL PRIMARY KEY,
    executor_id          INT REFERENCES executor (id) ON DELETE CASCADE NOT NULL,
    activity_area_id     INT REFERENCES activity_area (id),
    work_experience_id   INT REFERENCES work_experience (id),
    user_status_id       INT REFERENCES user_status (id),
    information_yourself TEXT
);

CREATE TABLE work
(
    id          SERIAL PRIMARY KEY,
    executor_id INT REFERENCES executor (id) ON DELETE CASCADE NOT NULL,
    name        VARCHAR(124)                                   NOT NULL,
    date_added  TIMESTAMP                                      NOT NULL,
    description TEXT                                           NOT NULL,
    file        TEXT                                           NOT NULL,
    type        VARCHAR(124)                                   NOT NULL
);

INSERT INTO activity_area (type)
VALUES ('Видео-креатор'),
       ('Монтажер'),
       ('Рилс-мейкер'),
       ('Фотограф'),
       ('СММ');

INSERT INTO user_status (category)
VALUES ('ищу работу'),
       ('рассматриваю предложения'),
       ('не ищу работу'),
       ('ищу заказ');

INSERT INTO work_experience (category)
VALUES ('>1'),
       ('2'),
       ('3'),
       ('4'),
       ('<5');

INSERT INTO person (chat_id, first_name, last_name, username_tg, phone)
VALUES (123456, 'Oleg', 'Polikarpov', 'olegPolikarpov', '+798123812'),
       (123457, 'Kirill', 'Minor', 'minKir', '+709123123'),
       (123458, 'Kola', 'Misa', 'mikola', '+798123123');

INSERT INTO image_of_person (person_chat_id, file_path)
VALUES  (123456, 'images\image_of_profile\0.png'),
        (123457, null),
        (123458, null);

INSERT INTO executor (person_chat_id)
VALUES (123456),
       (123457),
       (123458);