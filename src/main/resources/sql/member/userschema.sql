# CREATE DATABASE if not exists finalproject;


use finalproject;


delete
from finalproject.chat_message;
delete
from finalproject.chat_room;


delete
from finalproject.area_tourism;
delete
from finalproject.rent_info;
delete
from finalproject.ticket_info;
delete
from finalproject.qna;
delete
from finalproject.comment;
delete
from finalproject.accompany;
delete
from finalproject.plan_board_detail;
delete
from finalproject.plan_board;
delete
from finalproject.car_info;
delete
from finalproject.com_info;
delete
from finalproject.user;
delete
from finalproject.manager;


alter table finalproject.chat_message
    auto_increment = 1;
alter table finalproject.chat_room
    auto_increment = 1;
alter table finalproject.rent_info
    auto_increment = 1;
alter table finalproject.user
    auto_increment = 1;
alter table finalproject.manager
    auto_increment = 1;
alter table finalproject.ticket_info
    auto_increment = 1;
alter table finalproject.area_tourism
    auto_increment = 1;
alter table finalproject.qna
    auto_increment = 1;
alter table finalproject.accompany
    auto_increment = 1;
alter table finalproject.comment
    auto_increment = 1;
alter table finalproject.rent_info
    auto_increment = 1;
alter table finalproject.plan_board
    auto_increment = 1;
alter table finalproject.plan_board_detail
    auto_increment = 1;
alter table finalproject.car_info
    auto_increment = 1;
alter table finalproject.com_info
    auto_increment = 1;

CREATE TABLE if not exists `user`
(
    `user_id`       varchar(30) NOT NULL,
    `user_number`   int         NOT NULL primary key auto_increment,
    `user_name`     varchar(10) NOT NULL,
    `user_password` varchar(16) NOT NULL,
    `user_phone`    varchar(15) NULL,
    `user_gender`   varchar(1)  NOT NULL,
    `user_date`     date        NOT NULL,
    `user_mbti`     varchar(4)  NULL,
    `user_img`      varchar(20) NULL,
    `user_regdate`  date        NOT NULL

);


CREATE TABLE if not exists `ticket_info`
(
    `tic_ticket_id`             bigint       NOT NULL,
    `tic_flight_departure_date` varchar(100) NOT NULL,
    `tic_flight_arrival_date`   varchar(100) NOT NULL,
    `tic_airline_logo`          varchar(100) NOT NULL,
    `tic_seat_grade`            varchar(100) NOT NULL,
    `tic_airline_name`          varchar(100) NOT NULL,
    `tic_fee`                   int          NOT NULL,
    `tic_from_location`         varchar(100) NOT NULL,
    `tic_to_location`           varchar(100) NOT NULL,
    `tic_vihicle_id`            varchar(100) NOT NULL,
    `user_number`               bigint       NOT NULL
);

CREATE TABLE if not exists `rent_info`
(
    rent_reservation_id bigint       NOT NULL primary key auto_increment,
    rent_name           VARCHAR(255) NOT NULL,
    rent_type           VARCHAR(255) NOT NULL,
    rent_company        VARCHAR(255) NOT NULL,
    rent_option         VARCHAR(255) NOT NULL,
    rent_price          VARCHAR(255) NOT NULL,
    rent_departure_date VARCHAR(255) NOT NULL,
    rent_arrival_date   VARCHAR(255) NOT NULL,
    rent_img            VARCHAR(255) NOT NULL,
    rent_accommodate    BIGINT       NOT NULL,
    rent_year           VARCHAR(255) NOT NULL,
    rent_oil            VARCHAR(255) NOT NULL,
    tid                 VARCHAR(255) NOT NULL,
    user_number         bigint       NOT NULL

);

CREATE TABLE if not exists `area_tourism`
(
    `area_id`      bigint       not null primary key auto_increment,
    `area_name`    varchar(100) not null,
    `area_name_ko` varchar(100) not null,
    `place_id`     int          not null,
    `place_name`   varchar(100) not null
);


CREATE TABLE if not exists `manager`
(
    `id`               int         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `manager_id`       varchar(30) NOT NULL,
    `manager_password` varchar(30) NOT NULL

);



CREATE TABLE if not exists `qna`
(
    `qna_number`  int           NOT NULL primary key auto_increment,
    `qna_writer`  varchar(30)   NOT NULL,
    `qna_title`   varchar(100)  NOT NULL,
    `qna_content` varchar(5000) NOT NULL,
    `qna_date`    date          NULL,
    `qna_answer`  varchar(500)  NULL
);



CREATE TABLE if not exists car_info
(
    car_info_id  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_name     VARCHAR(255) NOT NULL,
    car_year     VARCHAR(255) NOT NULL,
    car_nation   VARCHAR(255) NOT NULL,
    car_type     VARCHAR(255) NOT NULL,
    oil_type     VARCHAR(255) NOT NULL,
    driver_age   VARCHAR(255) NOT NULL,
    car_people   INT          NOT NULL,
    car_price    INT          NOT NULL,
    car_discount INT          NOT NULL,
    com_id       INT          NOT NULL,
    car_option   VARCHAR(255) NOT NULL,
    car_img      varchar(20)  NOT NULL
);


CREATE TABLE if not exists com_info
(
    com_id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    com_name     VARCHAR(50)  NOT NULL,
    com_location VARCHAR(255) NOT NULL,
    com_tel      VARCHAR(20)  NULL,
    com_opentime VARCHAR(255) NOT NULL
);



CREATE TABLE if not exists accompany
(
    ac_num          INT AUTO_INCREMENT PRIMARY KEY,
    user_number     int,
    ac_regdate      DATE,
    ac_title        VARCHAR(100),
    ac_text         VARCHAR(3000),
    ac_people       INT,
    ac_region       VARCHAR(255),
    ac_startdate    DATE,
    ac_enddate      DATE,
    ac_status       VARCHAR(50),
    ac_picture      VARCHAR(255),
    ac_viewcount    INT,
    ac_travelstyle  VARCHAR(50),
    ac_personalhash VARCHAR(255),
    FOREIGN KEY (user_number) REFERENCES user (user_number)
);


create table if not exists `comment`
(
    co_number   INT AUTO_INCREMENT PRIMARY KEY,
    co_content  varchar(3000),
    co_regdate  DATETIME,
    user_number int,
    ac_num      INT,
    FOREIGN KEY (user_number) REFERENCES user (user_number),
    FOREIGN KEY (ac_num) REFERENCES accompany (ac_num)
);


create table if not exists chat_room
(

    room_id      bigint primary key auto_increment,
    user_number1 bigint not null, -- user table의 userNumber를 참고하는 컬럼
    user_number2 bigint not null,

    FOREIGN KEY (user_number1) REFERENCES user (user_number),
    FOREIGN KEY (user_number2) REFERENCES user (user_number)


);


create table if not exists chat_message
(

    message_id bigint primary key auto_increment,
    room_id    bigint                 not null,
    content    varchar(3000),
    send_time  datetime default now() not null,
    sender_id  bigint                 not null,

    FOREIGN KEY (room_id) REFERENCES chat_room (room_id),
    FOREIGN KEY (sender_id) REFERENCES user (user_number)

);
