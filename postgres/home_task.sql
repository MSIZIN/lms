CREATE TABLE home_task (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	course_id BIGINT NOT NULL REFERENCES course(id),
	name VARCHAR(150) NOT NULL,
	start_date DATE NOT NULL,
	finish_date DATE NOT NULL,
	description TEXT NOT NULL,
	CONSTRAINT check_dates CHECK (start_date <= finish_date)
);

INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES (1, 'Медицинская маска', '2020-09-11', '2020-10-11', 'Как правильно носить маску?');
INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES (1, 'Выживание без стипендии', '2020-11-26', '2021-02-01', 'Как выжить без стипендии?');
INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES (2, 'Введение имени', '2020-09-01', '2020-11-02', 'Введите ваше имя.');
INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES (3, 'В последний момент', '2020-10-15', '2020-12-01', 'Нужно прислать любой текст. ВНИМАНИЕ! Прислать его нужно обязательно в последний день дедлайна.');
INSERT INTO home_task(course_id, name, start_date, finish_date, description) VALUES (3, 'Будущее задание', '2021-01-25', '2021-03-02', 'Пока нет смысла придумывать описание. Как задание появится, тогда добавим описание.');