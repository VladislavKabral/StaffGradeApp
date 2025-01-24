create table feedbacks (
    id uuid primary key,
    package_id uuid not null references packages(id),
    source_user_id uuid not null,
    status_id uuid not null references statuses(id),
    completed_at date
)