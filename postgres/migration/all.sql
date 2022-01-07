CREATE TABLE person
(
    verification_code UUID        NOT NULL PRIMARY KEY,
    position          VARCHAR(50) NOT NULL,
    first_name        VARCHAR(50) NOT NULL,
    last_name         VARCHAR(50) NOT NULL,
    middle_name       VARCHAR(50) NOT NULL,
    phone             VARCHAR(50),
    home_town         VARCHAR(50),
    info              TEXT,
    vk_link           VARCHAR(150),
    facebook_link     VARCHAR(150),
    linkedin_link     VARCHAR(150),
    instagram_link    VARCHAR(150),
    CONSTRAINT check_position CHECK (position = 'студент' OR position = 'преподаватель')
);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 'преподаватель', 'Елизавета', 'Балтинова', 'Игоревна',
        '+7 988 737 7846', 'Калуга', 'Люблю рис.', 'https://vk.com/id119189714', 'https://facebook.com/id8591854766',
        'https://linkedin.com/id8591854766', 'https://instagram.com/id859189456');
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 'преподаватель', 'Иван', 'Хрусталёв', 'Николаевич', null, null,
        'Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.',
        'https://vk.com/id119489714', null, null, 'https://instagram.com/id859213456');
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('ef9c5278-1afa-4779-901f-14ec32939f74', 'преподаватель', 'Николай', 'Дубровский', 'Фёдорович',
        '+7 930 567 6243', 'Белгород', null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 'студент', 'Александр', 'Сизинцев', 'Константинович', '+7 919 374 9325',
        'Воронеж', 'Читаю книги и реп.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 'студент', 'Даниил', 'Миронов', 'Николаевич', '+7 911 994 2760', null,
        null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 'студент', 'Галина', 'Епископова', 'Семёновна', '+7 977 190 3825',
        'Москва', 'Легко найти, тяжело потерять.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('14391b29-1a21-4028-a23b-5f2437595f05', 'студент', 'Николай', 'Белов', 'Ильич', null, null,
        'Мамин бродяга, папин симпотяга.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('bcceb87f-2357-43c5-95a7-1c5e8de37122', 'студент', 'Ирина', 'Федотова', 'Антоновна', null, null, null, null,
        null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('7bac6a27-60a4-4da4-883d-82070cb8b024', 'студент', 'Рафаэль', 'Плюмер', 'Кириллович', null, null, null, null,
        null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link,
                    facebook_link, linkedin_link, instagram_link)
values ('c3030b67-8845-43c7-94bb-821a2341c2ed', 'студент', 'Роман', 'Орехов', 'Альбертович', null, null, null, null,
        null, null, null);

CREATE TABLE educational_group
(
    id            BIGSERIAL   NOT NULL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    department    VARCHAR(50) NOT NULL,
    course_number INT         NOT NULL,
    CONSTRAINT unique_name UNIQUE (name)
);
INSERT INTO educational_group (name, department, course_number)
VALUES ('664', 'ФАЛТ', 1);
INSERT INTO educational_group (name, department, course_number)
VALUES ('551', 'ИНКБИСТ', 3);
INSERT INTO educational_group (name, department, course_number)
VALUES ('М05-13в', 'ФИВТ', 5);
INSERT INTO educational_group (name, department, course_number)
VALUES ('М05-13a', 'ФИВТ', 5);

CREATE TABLE account
(
    id                       BIGSERIAL    NOT NULL PRIMARY KEY,
    person_verification_code UUID         NOT NULL REFERENCES person (verification_code),
    email                    VARCHAR(150) NOT NULL,
    password                 VARCHAR(150) NOT NULL,
    CONSTRAINT unique_verification_code UNIQUE (person_verification_code),
    CONSTRAINT unique_email UNIQUE (email)
);
insert into account (person_verification_code, email, password)
values ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 'mbonsul0@scribd.com', 'unW4P2LAK4T0');
insert into account (person_verification_code, email, password)
values ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 'swotton1@sogou.com', 'fxsOr0hr');
insert into account (person_verification_code, email, password)
values ('ef9c5278-1afa-4779-901f-14ec32939f74', 'tbain2@purevolume.com', 'WwuWh16');
insert into account (person_verification_code, email, password)
values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 'lcescotti3@wiley.com', 'E7VYwVbYfdA');
insert into account (person_verification_code, email, password)
values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 'trosendorf4@t.co', 'lAIKLE1997');
insert into account (person_verification_code, email, password)
values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 'rboram5@ucsd.edu', 'BawU4W');
insert into account (person_verification_code, email, password)
values ('14391b29-1a21-4028-a23b-5f2437595f05', 'fevitts6@seattletimes.com', 'Ay0yY8jevs');

CREATE TABLE education_info
(
    id                BIGSERIAL   NOT NULL PRIMARY KEY,
    student_id        UUID        NOT NULL REFERENCES person (verification_code),
    group_id          BIGINT      NOT NULL REFERENCES educational_group (id),
    admission_year    INT         NOT NULL,
    degree            VARCHAR(50) NOT NULL,
    educational_form  VARCHAR(50) NOT NULL,
    educational_basis VARCHAR(50) NOT NULL,
    CONSTRAINT unique_student_id UNIQUE (student_id),
    CONSTRAINT check_degree CHECK (degree IN ('бакалавр', 'специалист', 'магистр')),
    CONSTRAINT check_educational_form CHECK (educational_form IN ('очная', 'заочная', 'вечерняя')),
    CONSTRAINT check_educational_basis CHECK (educational_basis IN ('контрактная', 'бюджетная'))
);
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 3, 2016, 'магистр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 3, 2016, 'магистр', 'заочная', 'контрактная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 3, 2016, 'магистр', 'вечерняя', 'контрактная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('14391b29-1a21-4028-a23b-5f2437595f05', 4, 2016, 'магистр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('bcceb87f-2357-43c5-95a7-1c5e8de37122', 1, 2020, 'бакалавр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('7bac6a27-60a4-4da4-883d-82070cb8b024', 1, 2020, 'бакалавр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis)
values ('c3030b67-8845-43c7-94bb-821a2341c2ed', 2, 2018, 'специалист', 'очная', 'бюджетная');

CREATE TABLE course
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    description TEXT
);
INSERT INTO course (name, description)
VALUES ('Выживание в экстремальных условиях',
        'Этот курс срочно нужно пройти всем студентам, чтобы выжить в этом году.');
INSERT INTO course (name, description)
VALUES ('Введение в введение', 'В этом курсе мы познакомимся с базовыми принципами введения.');
INSERT INTO course (name, description)
VALUES ('Основы прокрастинации', 'Описание курса добавится на следующей неделе.');

CREATE TABLE groups_courses
(
    group_id  BIGINT NOT NULL REFERENCES educational_group (id),
    course_id BIGINT NOT NULL REFERENCES course (id),
    PRIMARY KEY (group_id, course_id)
);
INSERT INTO groups_courses (group_id, course_id)
VALUES (1, 1);
INSERT INTO groups_courses (group_id, course_id)
VALUES (2, 1);
INSERT INTO groups_courses (group_id, course_id)
VALUES (3, 1);
INSERT INTO groups_courses (group_id, course_id)
VALUES (4, 1);
INSERT INTO groups_courses (group_id, course_id)
VALUES (3, 2);
INSERT INTO groups_courses (group_id, course_id)
VALUES (4, 2);
INSERT INTO groups_courses (group_id, course_id)
VALUES (3, 3);

CREATE TABLE teachers_courses
(
    teacher_id UUID   NOT NULL REFERENCES person (verification_code),
    course_id  BIGINT NOT NULL REFERENCES course (id),
    PRIMARY KEY (teacher_id, course_id)
);
INSERT INTO teachers_courses (teacher_id, course_id)
VALUES ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 1);
INSERT INTO teachers_courses (teacher_id, course_id)
VALUES ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 2);
INSERT INTO teachers_courses (teacher_id, course_id)
VALUES ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 3);
INSERT INTO teachers_courses (teacher_id, course_id)
VALUES ('ef9c5278-1afa-4779-901f-14ec32939f74', 3);

CREATE TABLE course_material
(
    id             BIGSERIAL    NOT NULL PRIMARY KEY,
    course_id      BIGINT       NOT NULL REFERENCES course (id),
    name           VARCHAR(150) NOT NULL,
    content        TEXT         NOT NULL,
    date_of_adding DATE         NOT NULL
);
INSERT INTO course_material(course_id, name, content, date_of_adding)
VALUES (1, 'Что делать, если вам звонят по незнакомому номеру, а вы интроверт?',
        '1) Просто проигнорируйте. 2) Если после этого вам позвонят ещё раз, смените номер вашего телефона',
        '2020-09-12');
INSERT INTO course_material(course_id, name, content, date_of_adding)
VALUES (1, 'Что делать, если вы прячетесь от убийцы, но вам хочется чихнуть?',
        'Просто не закрывайте глаза. Научно доказано, что чихнуть с закрытыми глазами невозможно', '2020-10-03');
INSERT INTO course_material(course_id, name, content, date_of_adding)
VALUES (2, 'Как правильно ввести в курс дела.', 'Нужно рассказать всё лаконично и без лишних подробностей.',
        '2020-09-23');
INSERT INTO course_material(course_id, name, content, date_of_adding)
VALUES (2, 'Как правильно вводить войска.', 'Нужно отдавать приказы лаконично и без лишних подробностей.',
        '2020-11-01');
INSERT INTO course_material(course_id, name, content, date_of_adding)
VALUES (3, 'Материал по первому занятию',
        'Кто-то из преподавателей добавит этот материл, как только пройдёт первое занятие', '2020-10-02');

CREATE TABLE group_leaders_courses
(
    student_id UUID   NOT NULL REFERENCES person (verification_code),
    course_id  BIGINT NOT NULL REFERENCES course (id),
    PRIMARY KEY (student_id, course_id)
);
INSERT INTO group_leaders_courses (student_id, course_id)
VALUES ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 1);
INSERT INTO group_leaders_courses (student_id, course_id)
VALUES ('c3030b67-8845-43c7-94bb-821a2341c2ed', 1);
INSERT INTO group_leaders_courses (student_id, course_id)
VALUES ('14391b29-1a21-4028-a23b-5f2437595f05', 2);
INSERT INTO group_leaders_courses (student_id, course_id)
VALUES ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 3);

CREATE TABLE home_task
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    course_id   BIGINT       NOT NULL REFERENCES course (id),
    name        VARCHAR(150) NOT NULL,
    start_date  DATE         NOT NULL,
    finish_date DATE         NOT NULL,
    description TEXT         NOT NULL,
    CONSTRAINT check_dates CHECK (start_date <= finish_date)
);
INSERT INTO home_task(course_id, name, start_date, finish_date, description)
VALUES (1, 'Медицинская маска', '2020-09-11', '2020-10-11', 'Как правильно носить маску?');
INSERT INTO home_task(course_id, name, start_date, finish_date, description)
VALUES (1, 'Выживание без стипендии', '2020-11-26', '2021-02-01', 'Как выжить без стипендии?');
INSERT INTO home_task(course_id, name, start_date, finish_date, description)
VALUES (2, 'Введение имени', '2020-09-01', '2020-11-02', 'Введите ваше имя.');
INSERT INTO home_task(course_id, name, start_date, finish_date, description)
VALUES (3, 'В последний момент', '2020-10-15', '2020-12-01',
        'Нужно прислать любой текст. ВНИМАНИЕ! Прислать его нужно обязательно в последний день дедлайна.');
INSERT INTO home_task(course_id, name, start_date, finish_date, description)
VALUES (3, 'Будущее задание', '2021-01-25', '2021-03-02',
        'Пока нет смысла придумывать описание. Как задание появится, тогда добавим описание.');

CREATE TABLE home_task_solution
(
    home_task_id   BIGINT NOT NULL REFERENCES home_task (id),
    student_id     UUID   NOT NULL REFERENCES person (verification_code),
    date_of_adding DATE   NOT NULL,
    content        TEXT   NOT NULL,
    PRIMARY KEY (home_task_id, student_id)
);
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (1, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2020-09-10', 'Нужно надеть её на лицо.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (3, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2021-01-01', 'Меня зовут Александр.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (4, '8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', '2020-12-01', 'Надеюсь, что я прислал задание в последний момент.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (1, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-09-18', 'Надо носить так, чтобы потом было нестыдно.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (2, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-11-27', 'Питаться духовной пищей.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (3, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-10-05', 'Мне имя – Вельзевул, хозяин стратосферы.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (4, '1242fe72-abfb-4722-ad8d-a08be313a9a1', '2020-10-01',
        'Не могу больше сдерживаться! Хочу отправить задание именно сейчас!');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (1, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-09-23',
        'Днём маска должна прикрывать рот, а ночью можно ею прикрывать глаза, т.е. использовать вместо маски для сна. Рекомендую!');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (2, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-10-01', 'Откуда мне знать, у меня то стипендия всегда есть.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (4, '96631d71-74d4-44dc-b9d8-dcedfb80bce3', '2020-12-01', 'А можно перенести это задание ещё на неделю?');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (1, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-09-14',
        'Как же правильно носить маску? Лучше спросите у Илона Маска.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (2, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-11-28', 'Заработать или украсть. Можно и то и то сразу.');
INSERT INTO home_task_solution (home_task_id, student_id, date_of_adding, content)
VALUES (3, '14391b29-1a21-4028-a23b-5f2437595f05', '2020-11-11', 'Николай, Коля, Колян, Колямбда.');