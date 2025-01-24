create table forms_questions (
    form_id uuid references forms(id),
    question_id uuid references questions(id),
    constraint forms_questions_pk primary key (form_id, question_id)
);