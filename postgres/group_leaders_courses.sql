CREATE TABLE group_leaders_courses (
	student_id UUID NOT NULL REFERENCES person(verification_code),
	course_id BIGINT NOT NULL REFERENCES course(id),
	PRIMARY KEY(student_id, course_id)
);

INSERT INTO group_leaders_courses (student_id, course_id) VALUES ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 1);
INSERT INTO group_leaders_courses (student_id, course_id) VALUES ('c3030b67-8845-43c7-94bb-821a2341c2ed', 1);
INSERT INTO group_leaders_courses (student_id, course_id) VALUES ('14391b29-1a21-4028-a23b-5f2437595f05', 2);
INSERT INTO group_leaders_courses (student_id, course_id) VALUES ('96631d71-74d4-44dc-b9d8-dcedfb80bce3', 3);