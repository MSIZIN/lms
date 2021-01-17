CREATE TABLE home_task_solution (
	home_task_id BIGINT NOT NULL REFERENCES home_task(id),
	student_id UUID NOT NULL REFERENCES person(verification_code),
	date_of_adding DATE NOT NULL,
	content TEXT NOT NULL,
	PRIMARY KEY(home_task_id, student_id)
);

INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (1, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2020-09-10', 'Нужно надеть её на лицо.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (3, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2021-01-01', 'Меня зовут Александр.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (4, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2020-12-01', 'Надеюсь, что я прислал задание в последний момент.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (1, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-09-18', 'Надо носить так, чтобы потом было нестыдно.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (2, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-11-27', 'Питаться духовной пищей.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (3, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-10-05', 'Мне имя – Вельзевул, хозяин стратосферы.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (4, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-10-01', 'Не могу больше сдерживаться! Хочу отправить задание именно сейчас!');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (1, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-09-23', 'Днём маска должна прикрывать рот, а ночью можно ею прикрывать глаза, т.е. использовать вместо маски для сна. Рекомендую!');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (2, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-10-01', 'Откуда мне знать, у меня то стипендия всегда есть.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (4, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-12-01', 'А можно перенести это задание ещё на неделю?');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (1, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-09-14', 'Как же правильно носить маску? Лучше спросите у Илона Маска.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (2, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-11-28', 'Заработать или украсть. Можно и то и то сразу.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content) VALUES (3, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-11-11', 'Николай, Коля, Колян, Колямбда.');