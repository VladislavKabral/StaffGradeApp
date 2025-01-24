create table questions (
    id uuid primary key,
    skill_id uuid not null references skills(id),
    text varchar(100) not null
)