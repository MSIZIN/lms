CREATE TABLE teachers_courses (
	teacher_id UUID NOT NULL REFERENCES person(verification_code),
	course_id BIGINT NOT NULL REFERENCES course(id),
	PRIMARY KEY(teacher_id, course_id)
);

INSERT INTO teachers_courses (teacher_id, course_id) VALUES ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 1);
INSERT INTO teachers_courses (teacher_id, course_id) VALUES ('36a777ae-ff14-48f7-9fb5-66334ebc0fcc', 2);
INSERT INTO teachers_courses (teacher_id, course_id) VALUES ('64ce37cb-c26b-433d-98c6-a6fd2d90926c', 3);
INSERT INTO teachers_courses (teacher_id, course_id) VALUES ('ef9c5278-1afa-4779-901f-14ec32939f74', 3);