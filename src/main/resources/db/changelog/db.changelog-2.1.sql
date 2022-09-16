--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO manual_tag_name (permanent_name, name, initial, type)
VALUES ('CPM_name', 'CPM_name', TRUE, '3622'),
       ('CPM_number', 'CPM_number', TRUE, '3622'),
       ('CPM_owner', 'CPM_owner', TRUE, '3622'),
       ('poverka_method', 'poverka_method', TRUE, '3622'),
       ('poverka_place', 'poverka_place', TRUE, '3622'),
       ('PR_name', 'PR_name', TRUE, '3622'),
       ('PR_number', 'PR_number', TRUE, '3622'),
       ('check_leakproofness', 'check_leakproofness', TRUE, '3622'),
       ('check_inspection', 'check_inspection', TRUE, '3622'),
       ('check_software', 'check_software', TRUE, '3622'),
       ('check_testing', 'check_testing', TRUE, '3622'),
       ('inspector_position', 'inspector_position', TRUE, '3622'),
       ('inspector_full_name', 'inspector_full_name', TRUE, '3622'),
       ('date', 'date', TRUE, '3622');