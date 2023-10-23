DROP DATABASE IF EXISTS hiring;
CREATE DATABASE `hiring`;
USE `hiring`;

CREATE TABLE user
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50),
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
)  ENGINE = INNODB
        DEFAULT CHARSET = utf8;


CREATE TABLE employee
(
    id INT(11) NOT NULL PRIMARY KEY,
    status BOOL NOT NULL,
    FOREIGN KEY (id) REFERENCES user(id)
        ON DELETE CASCADE
)  ENGINE = INNODB
         DEFAULT CHARSET = utf8;


CREATE TABLE employer
(
    id INT(11) NOT NULL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES user(id)
            ON DELETE CASCADE
)  ENGINE = INNODB
          DEFAULT CHARSET = utf8;


CREATE TABLE active_user
(
    token VARCHAR(100) NOT NULL PRIMARY KEY,
    id_user INT(11) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id)
        ON DELETE CASCADE
)  ENGINE = INNODB
          DEFAULT CHARSET = utf8;


CREATE TABLE vacancy
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    jobTitle VARCHAR(50) NOT NULL,
    salary INT NOT NULL,
    status BOOL NOT NULL,
    employer_id INT(11) NOT NULL,
    FOREIGN KEY (employer_id) REFERENCES employer(id)
        ON DELETE CASCADE
)  ENGINE = INNODB
       DEFAULT CHARSET = utf8;


CREATE TABLE skill
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    level INT(1) NOT NULL,
    employee_id INT(11) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
		ON DELETE CASCADE
)  ENGINE = INNODB
        DEFAULT CHARSET = utf8;


CREATE TABLE vacancy_requirement
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    level INT(1) NOT NULL,
    obligatory BOOL NOT NULL,
    vacancy_id INT(11) NOT NULL,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id) ON DELETE CASCADE
)  ENGINE = INNODB
           DEFAULT CHARSET = utf8;



