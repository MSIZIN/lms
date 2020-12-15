CREATE TABLE course (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(150) NOT NULL,
	description TEXT
);

INSERT INTO course (name, description) VALUES ('Выживание в экстремальных условиях', 'Этот курс срочно нужно пройти всем студентам, чтобы выжить в этом году.');
INSERT INTO course (name, description) VALUES ('Введение в введение', 'В этом курсе мы познакомимся с базовыми принципами введения.');
INSERT INTO course (name, description) VALUES ('Основы прокрастинации', 'Описание курса добавится на следующей неделе.');