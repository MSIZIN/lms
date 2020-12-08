CREATE TABLE educational_group (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	department VARCHAR(50) NOT NULL,
	course_number INT NOT NULL,
	CONSTRAINT unique_name UNIQUE (name)
);
INSERT INTO educational_group (name, department, course_number) VALUES ('664', 'ФАЛТ', 1);
INSERT INTO educational_group (name, department, course_number) VALUES ('551', 'ИНКБИСТ', 3);
INSERT INTO educational_group (name, department, course_number) VALUES ('М05-13в', 'ФИВТ', 5);
INSERT INTO educational_group (name, department, course_number) VALUES ('М05-13a', 'ФИВТ', 5);