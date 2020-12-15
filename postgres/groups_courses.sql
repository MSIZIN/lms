CREATE TABLE groups_courses (
	group_id BIGINT NOT NULL REFERENCES educational_group(id),
	course_id BIGINT NOT NULL REFERENCES course(id),
	PRIMARY KEY(group_id, course_id)
);

INSERT INTO groups_courses (group_id, course_id) VALUES (1, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (2, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (3, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (4, 1);
INSERT INTO groups_courses (group_id, course_id) VALUES (3, 2);
INSERT INTO groups_courses (group_id, course_id) VALUES (4, 2);
INSERT INTO groups_courses (group_id, course_id) VALUES (3, 3);