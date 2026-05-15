MERGE INTO book (id, title, author, published_year) KEY(id) VALUES (1, 'Tõde ja õigus', 'Anton Hansen Tammsaare', 1926);
MERGE INTO book (id, title, author, published_year) KEY(id) VALUES (2, 'Kevade', 'Oskar Luts', 1912);
MERGE INTO book (id, title, author, published_year) KEY(id) VALUES (3, 'Rehepapp', 'Andrus Kivirähk', 2000);
MERGE INTO book (id, title, author, published_year) KEY(id) VALUES (4, 'Mees, kes teadis ussisõnu', 'Andrus Kivirähk', 2007);
MERGE INTO book (id, title, author, published_year) KEY(id) VALUES (5, 'Nimed marmortahvlil', 'Albert Kivikas', 1936);
ALTER TABLE book ALTER COLUMN id RESTART WITH 6;
