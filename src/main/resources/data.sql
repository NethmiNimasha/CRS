USE crs;

-- Insert Users
INSERT INTO Users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO Users (username, password, role) VALUES ('student1', 'pass123', 'STUDENT');
INSERT INTO Users (username, password, role) VALUES ('faculty1', 'fac123', 'FACULTY');

-- Insert Students (Assuming user IDs are 1, 2, 3 respectively)
INSERT INTO Students (user_id, name, dob, program, year, contact) VALUES (2, 'John Doe', '2000-05-15', 'Computer Science', 2, 'john@example.com');

-- Insert Faculty
INSERT INTO Faculty (user_id, name, department) VALUES (3, 'Dr. Smith', 'Computer Science');

-- Insert Courses
INSERT INTO Courses (title, credits, department, max_capacity) VALUES ('Introduction to Programming', 3, 'Computer Science', 50);
INSERT INTO Courses (title, credits, department, max_capacity) VALUES ('Data Structures', 4, 'Computer Science', 30);
INSERT INTO Courses (title, credits, department, max_capacity) VALUES ('Advanced Algorithms', 4, 'Computer Science', 20);

-- Insert Prerequisites (Data Structures requires Intro to Programming)
-- Assuming Intro to Programming is ID 1, Data Structures is ID 2, Adv Algorithms is ID 3
INSERT INTO Prerequisites (course_id, prerequisite_course_id) VALUES (2, 1);
INSERT INTO Prerequisites (course_id, prerequisite_course_id) VALUES (3, 2);
