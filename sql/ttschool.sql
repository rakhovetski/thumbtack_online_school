DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`;
USE `ttschool`;

CREATE TABLE subject
(
    id   INT primary key auto_increment,
    name varchar(50) not null
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE school
(
    id   INT primary key auto_increment,
    name VARCHAR(50) not null,
    year INT         not null,
    CONSTRAINT school UNIQUE (name, year)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE `group`
(
    id       INT primary key auto_increment,
    name     varchar(50) not null,
    room     varchar(50) not null,
    school_id int         NOT NULL,
    FOREIGN KEY (school_id) REFERENCES school (id)
        ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;



CREATE TABLE subject_group
(
    id       INT NOT NULL AUTO_INCREMENT,
    group_id   INT NOT NULL,
	subject_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (group_id) REFERENCES `group` (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE
);

CREATE TABLE trainee
(
    id        INT primary key auto_increment,
    firstName VARCHAR(50) not null,
    lastName  VARCHAR(50) not null,
    rating    INT,
    group_id   INT,
    FOREIGN KEY (group_id) REFERENCES `group` (id)
        ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

