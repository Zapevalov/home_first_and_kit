INSERT INTO public.t_roles (role_id, created_at, updated_at, name)
VALUES (default, current_timestamp, current_timestamp, 'ROLE_USER')
on conflict do nothing;
INSERT INTO public.t_roles (role_id, created_at, updated_at, name)
VALUES (default, current_timestamp, current_timestamp, 'ROLE_ADMIN')
on conflict do nothing;

INSERT INTO public.t_user (user_id, created_at, updated_at, birth_day, email, firstname, lastname, password, status,
                           username)
VALUES (default, current_timestamp, current_timestamp, NULL, 'admin@gmail.com', NULL, NULL,
        '{bcrypt}$2a$10$CCd9RYKobBoc/eFfr0Pjme2OjJ2oYWDbtlk1CxTVvwNlGcShsMfa6', 'ACTIVE', 'admin')
on conflict do nothing;;