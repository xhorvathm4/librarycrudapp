INSERT INTO person (first_name, last_name) VALUES ('Peter', 'Prvý');
INSERT INTO person (first_name, last_name) VALUES ('Lukáš', 'Druhý');
INSERT INTO person (first_name, last_name) VALUES ('Matej', 'Tretí');
INSERT INTO person (first_name, last_name) VALUES ('Jozef', 'Štvrtý');

INSERT INTO book (name, author) VALUES ('Starec a more', 'Ernest Hemingway');
INSERT INTO book (name, author) VALUES ('Rómeo a Júlia', 'William Shakespeare');
INSERT INTO book (name, author) VALUES ('Krvavé sonety', 'Pavol Országh Hviezdoslav');
INSERT INTO book (name, author) VALUES ('Hájnikova žena', 'Pavol Országh Hviezdoslav');
INSERT INTO book (name, author) VALUES ('Hamlet', 'William Shakespeare');
INSERT INTO book (name, author) VALUES ('Živý bič', 'Milo Urban');

INSERT INTO loan(book_id, person_id, borrowed_from) VALUES (2, 2, '2018-06-16');
INSERT INTO loan(book_id, person_id, borrowed_from) VALUES (1, 1, '2016-03-25');
INSERT INTO loan(book_id, person_id, borrowed_from) VALUES (3, 3, '2017-02-01');
INSERT INTO loan(book_id, person_id, borrowed_from) VALUES (5, 4, '2009-10-25');