insert into users(id, lastname, firstname, email, password, manager_id, position_id, status_id, team_id)
values ('3f9abd58-c15d-4ba4-b9f4-0e2e81b961d9',
        'Ivanov',
        'Ivan',
        'mr.ivanov@mail.ru',
        '$2a$12$PgRMYFkJbBSoWiWBJTHlE.1pPzxX7qBKzckFfRPbxGEiUAvDB5MF6',
        null,
        '16c01db4-a809-4b9f-9cc8-775643152a1b',
        '714ce9db-0dac-40ae-b882-c660df494257',
        null);

insert into users(id, lastname, firstname, email, password, manager_id, position_id, status_id, team_id)
values ('53d841a1-168d-43b9-b100-66ccb9ba1b21',
        'Petrov',
        'Petr',
        'mr.petrov@mail.ru',
        '$2a$12$PgRMYFkJbBSoWiWBJTHlE.1pPzxX7qBKzckFfRPbxGEiUAvDB5MF6',
        '3f9abd58-c15d-4ba4-b9f4-0e2e81b961d9',
        '04671784-5c61-4982-b2d9-1ffe6025ff2e',
        'c5cf8b0c-f4fa-4e02-88dd-d7665e53180c',
        null);

insert into users(id, lastname, firstname, email, password, manager_id, position_id, status_id, team_id)
values ('2503983d-d54b-4389-bf0b-b4b7b8c4163d',
        'Pupkin',
        'Konstantin',
        'mr.pupkin@mail.ru',
        '$2a$12$PgRMYFkJbBSoWiWBJTHlE.1pPzxX7qBKzckFfRPbxGEiUAvDB5MF6',
        '53d841a1-168d-43b9-b100-66ccb9ba1b21',
        '5352767a-aac7-4022-9ec8-fe78eef992f1',
        '714ce9db-0dac-40ae-b882-c660df494257',
        'e192cfca-fc40-45c4-a779-e471b30e5b81');

insert into users(id, lastname, firstname, email, password, manager_id, position_id, status_id, team_id)
values ('516984ea-1897-46ab-a5bf-064aa41f1852',
        'Hatfield',
        'Anya',
        'ms.hatfield@mail.ru',
        '$2a$12$PgRMYFkJbBSoWiWBJTHlE.1pPzxX7qBKzckFfRPbxGEiUAvDB5MF6',
        '3f9abd58-c15d-4ba4-b9f4-0e2e81b961d9',
        '1021789a-f202-43cd-bb36-0db19287a3a7',
        '714ce9db-0dac-40ae-b882-c660df494257',
        null);

insert into users(id, lastname, firstname, email, password, manager_id, position_id, status_id, team_id)
values ('8752ca80-f4c4-4f3e-8287-2b846e7935e8',
        'Phelps',
        'Rafael',
        'mr.phelps@mail.ru',
        '$2a$12$PgRMYFkJbBSoWiWBJTHlE.1pPzxX7qBKzckFfRPbxGEiUAvDB5MF6',
        '516984ea-1897-46ab-a5bf-064aa41f1852',
        'a5436a1b-be1e-41c4-ae45-92a18c8cc519',
        '714ce9db-0dac-40ae-b882-c660df494257',
        null);