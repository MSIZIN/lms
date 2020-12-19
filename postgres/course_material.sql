CREATE TABLE course_material (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	course_id BIGINT NOT NULL REFERENCES course(id),
	name VARCHAR(150) NOT NULL,
	content TEXT NOT NULL,
	date_of_adding DATE NOT NULL
);

INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES (1, 'Что делать, если вам звонят по незнакомому номеру, а вы интроверт?', '1) Просто проигнорируйте. 2) Если после этого вам позвонят ещё раз, смените номер вашего телефона', '2020-09-12');
INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES (1, 'Что делать, если вы прячетесь от убийцы, но вам хочется чихнуть?', 'Просто не закрывайте глаза. Научно доказано, что чихнуть с закрытыми глазами невозможно', '2020-10-03');
INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES (2, 'Как правильно ввести в курс дела.', 'Нужно рассказать всё лаконично и без лишних подробностей.', '2020-09-23');
INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES (2, 'Как правильно вводить войска.', 'Нужно отдавать приказы лаконично и без лишних подробностей.', '2020-11-01');
INSERT INTO course_material(course_id, name, content, date_of_adding) VALUES (3, 'Материал по первому занятию', 'Кто-то из преподавателей добавит этот материл, как только пройдёт первое занятие', '2020-10-02');