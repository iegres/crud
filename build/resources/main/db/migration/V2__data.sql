INSERT INTO author
            (id,
             NAME,
             books)
VALUES      ('531e4cdd-bb78-4769-a0c7-cb948a9f1238',
             'John',
             2);

INSERT INTO author
            (id,
             NAME,
             books)
VALUES      ('6924b3ad-a7e7-4a9a-8773-58f89ef88509',
             'Jack',
             1);

INSERT INTO book
            (id,
             NAME, 
             author_id)
VALUES      ('aeebbc96-52be-43fa-8c01-61b9fbca8fd7',
             'Tales',
             '531e4cdd-bb78-4769-a0c7-cb948a9f1238');

INSERT INTO book
            (id,
             NAME, 
             author_id)
VALUES      ('a6e54dad-a5a6-46c6-92b0-d61a78abb142',
             'Poems',
             '531e4cdd-bb78-4769-a0c7-cb948a9f1238');

INSERT INTO book
            (id,
             NAME, 
             author_id)
VALUES      ('13710917-7469-4bd7-91cc-af8df36213c9',
             'Novels',
             '6924b3ad-a7e7-4a9a-8773-58f89ef88509');
