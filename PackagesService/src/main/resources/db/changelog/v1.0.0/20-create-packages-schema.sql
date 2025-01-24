create table packages (
    id uuid primary key,
    name varchar(50) not null,
    target_user_id uuid not null,
    form_id uuid not null,
    is_public boolean not null,
    created_at date not null
)