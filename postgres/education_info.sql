CREATE TABLE education_info (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	student_id UUID NOT NULL REFERENCES person(verification_code),
	group_id BIGINT NOT NULL REFERENCES educational_group(id),
	admission_year INT NOT NULL,
	degree VARCHAR(50) NOT NULL,
	educational_form VARCHAR(50) NOT NULL,
	educational_basis VARCHAR(50) NOT NULL,
	CONSTRAINT unique_student_id UNIQUE (student_id),
	CONSTRAINT check_degree CHECK (degree IN ('бакалавр', 'специалист', 'магистр')),
	CONSTRAINT check_educational_form CHECK (educational_form IN ('очная', 'заочная', 'вечерняя')),
	CONSTRAINT check_educational_basis CHECK (educational_basis IN ('контрактная', 'бюджетная'))
);

insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 3, 2016, 'магистр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 3, 2016, 'магистр', 'заочная', 'контрактная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 3, 2016, 'магистр', 'вечерняя', 'контрактная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('14391b29-1a21-4028-a23b-5f2437595f05', 4, 2016, 'магистр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('bcceb87f-2357-43c5-95a7-1c5e8de37122', 1, 2020, 'бакалавр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('7bac6a27-60a4-4da4-883d-82070cb8b024', 1, 2020, 'бакалавр', 'очная', 'бюджетная');
insert into education_info (student_id, group_id, admission_year, degree, educational_form, educational_basis) values ('c3030b67-8845-43c7-94bb-821a2341c2ed', 2, 2018, 'специалист', 'очная', 'бюджетная');