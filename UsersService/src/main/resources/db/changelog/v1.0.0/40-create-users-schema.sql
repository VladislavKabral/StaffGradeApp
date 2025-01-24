create table users (
    id uuid primary key,
    lastname varchar(50) not null,
    firstname varchar(50) not null,
    email varchar(50) not null unique,
    password varchar(250) not null,
    manager_id uuid,
    position_id uuid references positions(id) on delete set null,
    status_id uuid references statuses(id) on delete set null,
    team_id uuid references teams(id) on delete set null
)