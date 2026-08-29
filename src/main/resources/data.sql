INSERT INTO app_users
(name, email, password, role, refresh_token, enabled, created_at, updated_at)
VALUES
    ('Rajeev', 'rajeev@gmail.com',
     '$2a$10$zwWkZjaDNKIxpW/MBS.wyuFk95WsiH/eVu5CPXZtsRPbnnO4vew3m',
     'USER', NULL, true, CURRENT_DATE, CURRENT_DATE),

    ('Rahul', 'rahul@gmail.com',
     '$2a$10$zwWkZjaDNKIxpW/MBS.wyuFk95WsiH/eVu5CPXZtsRPbnnO4vew3m',
     'USER', NULL,true, CURRENT_DATE, CURRENT_DATE),

    ('Amit', 'amit@gmail.com',
     '$2a$10$zwWkZjaDNKIxpW/MBS.wyuFk95WsiH/eVu5CPXZtsRPbnnO4vew3m',
     'USER',NULL, true, CURRENT_DATE, CURRENT_DATE),

    ('Admin', 'admin@gmail.com',
     '$2a$10$zwWkZjaDNKIxpW/MBS.wyuFk95WsiH/eVu5CPXZtsRPbnnO4vew3m',
     'ADMIN', NULL,true, CURRENT_DATE, CURRENT_DATE),

    ('Manager', 'manager@gmail.com',
     '$2a$10$zwWkZjaDNKIxpW/MBS.wyuFk95WsiH/eVu5CPXZtsRPbnnO4vew3m',
     'ADMIN', NULL,true, CURRENT_DATE, CURRENT_DATE);