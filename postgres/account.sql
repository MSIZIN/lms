CREATE TABLE account (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	person_verification_code UUID NOT NULL REFERENCES person(verification_code),
	email VARCHAR(150) NOT NULL,
	password VARCHAR(150) NOT NULL,
	CONSTRAINT unique_verification_code UNIQUE (person_verification_code),
	CONSTRAINT unique_email UNIQUE (email)
);

insert into account (person_verification_code, email, password) values ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 'mbonsul0@scribd.com', 'unW4P2LAK4T0');
insert into account (person_verification_code, email, password) values ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 'swotton1@sogou.com', 'fxsOr0hr');
insert into account (person_verification_code, email, password) values ('ef9c5278-1afa-4779-901f-14ec32939f74', 'tbain2@purevolume.com', 'WwuWh16');
insert into account (person_verification_code, email, password) values ('8cb979fe-0fcb-433a-b7ad-96a6e97ee71d', 'lcescotti3@wiley.com', 'E7VYwVbYfdA');
insert into account (person_verification_code, email, password) values ('1242fe72-abfb-4722-ad8d-a08be313a9a1', 'trosendorf4@t.co', 'lAIKLE1997');
insert into account (person_verification_code, email, password) values ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 'rboram5@ucsd.edu', 'BawU4W');
insert into account (person_verification_code, email, password) values ('14391b29-1a21-4028-a23b-5f2437595f05', 'fevitts6@seattletimes.com', 'Ay0yY8jevs');