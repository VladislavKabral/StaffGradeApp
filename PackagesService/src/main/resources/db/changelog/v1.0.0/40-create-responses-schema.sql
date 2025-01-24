create table responses (
    id uuid primary key ,
    feedback_id uuid not null,
    question_id uuid not null,
    rate double precision not null,
    text varchar(50) not null
);