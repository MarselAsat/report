--liquibase formatted sql

--changeset alina.parfenteva:1
INSERT INTO manual_tag_name (permanent_name, name, description, initial, type)
VALUES ('measureCount', 'tag_' || 'measureCount', '', TRUE, '3622'),
       ('pointsCount', 'tag_' || 'pointsCount', '', TRUE, '3622');