create table skills (
    id uuid primary key,
    name varchar(50) not null unique,
    description varchar(250) not null
)