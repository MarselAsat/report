--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('kmhMassmByMassm', 'КМХ рабочего МПР по контрольному', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('protocolNumber', 'kmhMassmByMassm.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhMassmByMassm'),
       ('ilNumber', 'kmhMassmByMassm.' || 'ilNumber',
        'Номер измерительной линии',
        TRUE, 'kmhMassmByMassm'),
       ('siknNumber', 'kmhMassmByMassm.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhMassmByMassm'),
       ('place_name', 'kmhMassmByMassm.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'kmhMassmByMassm'),

       -- Таблица 1
       ('work_mpr_sensor_type', 'kmhMassmByMassm.' || 'work_mpr_sensor_type',
        'Тип, марка сенсора рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('work_mpr_pep_type', 'kmhMassmByMassm.' || 'work_mpr_pep_type',
        'Тип, марка ПЭП рабочего МПР', TRUE, 'kmhMassmByMassm'),

       ('contr_mpr_sensor_type', 'kmhMassmByMassm.' || 'contr_mpr_sensor_type',
        'Тип, марка сенсора контрольного МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_pep_type', 'kmhMassmByMassm.' || 'contr_mpr_pep_type',
        'Тип, марка ПЭП контрольного МПР', TRUE, 'kmhMassmByMassm'),

       ('work_mpr_sensor_number', 'kmhMassmByMassm.' || 'work_mpr_sensor_number',
        'Заводской номер сенсора рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('work_mpr_pep_number', 'kmhMassmByMassm.' || 'work_mpr_pep_number',
        'Заводской номер ПЭП рабочего МПР', TRUE, 'kmhMassmByMassm'),

       ('contr_mpr_sensor_number', 'kmhMassmByMassm.' || 'contr_mpr_sensor_number',
        'Заводской номер сенсора контрольного МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_pep_number', 'kmhMassmByMassm.' || 'contr_mpr_pep_number',
        'Заводской номер ПЭП контрольного МПР', TRUE, 'kmhMassmByMassm'),

       ('work_mpr_date', 'kmhMassmByMassm.' || 'work_mpr_date',
        'Дата последней поверки рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_date', 'kmhMassmByMassm.' || 'contr_mpr_date',
        'Дата последней поверки контрольного МПР', TRUE, 'kmhMassmByMassm'),

       -- Таблица 2
       ('mass', 'kmhMassmByMassm.' || 'mass',
        'Заданная масса, т',
        TRUE, 'kmhMassmByMassm'),
       ('Q', 'kmhMassmByMassm.' || 'Q',
        'Расход, т/ч',
        TRUE, 'kmhMassmByMassm'),
       ('M_il', 'kmhMassmByMassm.' || 'M_il',
        '',
        TRUE, 'kmhMassmByMassm'),
       ('M_ilk', 'kmhMassmByMassm.' || 'M_ilk',
        '',
        TRUE, 'kmhMassmByMassm'),

       -- Нижняя часть
       ('deliver_org', 'kmhMassmByMassm.' || 'deliver_org',
        'Организация сдающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('deliver_name', 'kmhMassmByMassm.' || 'deliver_name',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('receiver_org', 'kmhMassmByMassm.' || 'receiver_org',
        'Организация принимающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('receiver_name', 'kmhMassmByMassm.' || 'receiver_name',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('service_org', 'kmhMassmByMassm.' || 'service_org',
        'Название сервисной организации',
        TRUE, 'kmhMassmByMassm'),
       ('service_name', 'kmhMassmByMassm.' || 'service_name',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhMassmByMassm');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('delta_M', 'kmhMassmByMassm.' || 'delta_M',
        '',
        FALSE, 'kmhMassmByMassm'),
       ('delta_max', 'kmhMassmByMassm.' || 'delta_max',
        '',
        FALSE, 'kmhMassmByMassm'),
       ('conclusion', 'kmhMassmByMassm.' || 'conclusion',
        'годен/не годен',
        FALSE, 'kmhMassmByMassm');