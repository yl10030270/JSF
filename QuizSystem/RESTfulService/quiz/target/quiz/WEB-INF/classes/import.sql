--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
INSERT INTO User (idUser, loginName, password, fname, lname, studentNumber) VALUES (0, 'user1', 'user1', 'user', '1', '01');

INSERT INTO Quiz (week) VALUES (1);
INSERT INTO Quiz (week) VALUES (2);
INSERT INTO Quiz (week) VALUES (3);
INSERT INTO Quiz (week) VALUES (4);

INSERT INTO Question (idQuiz, questionText, answer) VALUES (1, '1+1=', '2');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (2, '1+2=', '3');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (2, '1+3=', '4');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (3, '1+4=', '5');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (3, '1+5=', '6');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (3, '1+6=', '7');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (4, '1+7=', '8');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (4, '1+8=', '9');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (4, '1+9=', '10');
INSERT INTO Question (idQuiz, questionText, answer) VALUES (4, '1+10=', '11');

INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (1, 'a', '0');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (1, 'b', '1');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (1, 'c', '2');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (1, 'd', '3');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (1, 'e', '4');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (2, 'a', '2');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (2, 'b', '3');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (2, 'c', '4');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (2, 'd', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (3, 'a', '4');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (3, 'b', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (3, 'c', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (3, 'd', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (4, 'a', '4');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (4, 'b', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (4, 'c', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (4, 'd', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (5, 'a', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (5, 'b', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (5, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (5, 'd', '8');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (6, 'a', '3');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (6, 'b', '4');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (6, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (6, 'd', '8');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (7, 'a', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (7, 'b', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (7, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (7, 'd', '8');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (8, 'a', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (8, 'b', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (8, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (8, 'd', '9');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (9, 'a', '10');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (9, 'b', '1');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (9, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (9, 'd', '8');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (10, 'a', '5');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (10, 'b', '6');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (10, 'c', '7');
INSERT INTO QuestionChoice (idQuestion, choiceIndex, choiceText) VALUES (10, 'd', '11');
