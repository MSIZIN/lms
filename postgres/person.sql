CREATE TABLE person (
	verification_code UUID NOT NULL PRIMARY KEY,
	position VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	middle_name VARCHAR(50) NOT NULL,
	phone VARCHAR(50),
	home_town VARCHAR(50),
	info TEXT,
	vk_link VARCHAR(150),
	facebook_link VARCHAR(150),
	linkedin_link VARCHAR(150),
	instagram_link VARCHAR(150),
	CONSTRAINT check_position CHECK (position='студент' OR position='преподаватель')
);

insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 'преподаватель', 'Елизавета', 'Балтинова', 'Игоревна', '+7 988 737 7846', 'Калуга', 'Люблю рис.', 'https://vk.com/id119189714', 'https://facebook.com/id8591854766', 'https://linkedin.com/id8591854766', 'https://instagram.com/id859189456');
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 'преподаватель', 'Иван', 'Хрусталёв', 'Николаевич', null, null, 'Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.', 'https://vk.com/id119489714', null, null, 'https://instagram.com/id859213456');
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('ef9c5278-1afa-4779-901f-14ec32939f74', 'преподаватель', 'Николай', 'Дубровский', 'Фёдорович', '+7 930 567 6243', 'Белгород', null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 'студент', 'Александр', 'Сизинцев', 'Константинович', '+7 919 374 9325', 'Воронеж', 'Читаю книги и реп.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 'студент', 'Даниил', 'Миронов', 'Николаевич', '+7 911 994 2760', null, null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 'студент', 'Галина', 'Епископова', 'Семёновна', '+7 977 190 3825', 'Москва', 'Легко найти, тяжело потерять.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('14391b29-1a21-4028-a23b-5f2437595f05', 'студент', 'Николай', 'Белов', 'Ильич', null, null, 'Мамин бродяга, папин симпотяга.', null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('bcceb87f-2357-43c5-95a7-1c5e8de37122', 'студент', 'Ирина', 'Федотова', 'Антоновна', null, null, null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('7bac6a27-60a4-4da4-883d-82070cb8b024', 'студент', 'Рафаэль', 'Плюмер', 'Кириллович', null, null, null, null, null, null, null);
insert into person (verification_code, position, first_name, last_name, middle_name, phone, home_town, info, vk_link, facebook_link, linkedin_link, instagram_link) values ('c3030b67-8845-43c7-94bb-821a2341c2ed', 'студент', 'Роман', 'Орехов', 'Альбертович', null, null, null, null, null, null, null);