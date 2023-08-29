--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

--changeset alina.parfenteva:2
-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('protocolNumber', 'kmhMassByMass.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('ilNumber', 'kmhMassByMass.' || 'ilNumber',
        'Номер измерительной линии',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('siknNumber', 'kmhMassByMass.' || 'siknNumber',
        'СИКН №',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('place_name', 'kmhMassByMass.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'KMH_MASSM_BY_MASSM'),

       -- Таблица 1
       ('work_mpr_sensor_type', 'kmhMassByMass.' || 'work_mpr_sensor_type',
        'Тип, марка сенсора рабочего МПР',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('work_mpr_pep_type', 'kmhMassByMass.' || 'work_mpr_pep_type',
        'Тип, марка ПЭП рабочего МПР', TRUE, 'KMH_MASSM_BY_MASSM'),

       ('contr_mpr_sensor_type', 'kmhMassByMass.' || 'contr_mpr_sensor_type',
        'Тип, марка сенсора контрольного МПР',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('contr_mpr_pep_type', 'kmhMassByMass.' || 'contr_mpr_pep_type',
        'Тип, марка ПЭП контрольного МПР', TRUE, 'KMH_MASSM_BY_MASSM'),

       ('work_mpr_sensor_number', 'kmhMassByMass.' || 'work_mpr_sensor_number',
        'Заводской номер сенсора рабочего МПР',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('work_mpr_pep_number', 'kmhMassByMass.' || 'work_mpr_pep_number',
        'Заводской номер ПЭП рабочего МПР', TRUE, 'KMH_MASSM_BY_MASSM'),

       ('contr_mpr_sensor_number', 'kmhMassByMass.' || 'contr_mpr_sensor_number',
        'Заводской номер сенсора контрольного МПР',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('contr_mpr_pep_number', 'kmhMassByMass.' || 'contr_mpr_pep_number',
        'Заводской номер ПЭП контрольного МПР', TRUE, 'KMH_MASSM_BY_MASSM'),

       ('work_mpr_date', 'kmhMassByMass.' || 'work_mpr_date',
        'Дата последней поверки рабочего МПР',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('contr_mpr_date', 'kmhMassByMass.' || 'contr_mpr_date',
        'Дата последней поверки контрольного МПР', TRUE, 'KMH_MASSM_BY_MASSM'),

       -- Таблица 2
       ('mass', 'kmhMassByMass.' || 'mass',
        'Заданная масса, т',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('Q', 'kmhMassByMass.' || 'Q',
        'Расход, т/ч',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('M_il', 'kmhMassByMass.' || 'M_il',
        '',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('M_ilk', 'kmhMassByMass.' || 'M_ilk',
        '',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('delta_M', 'kmhMassByMass.' || 'delta_M',
        '',
        TRUE, 'KMH_MASSM_BY_MASSM'),

       -- Нижняя часть
       ('deliver_org', 'kmhMassByMass.' || 'deliver_org',
        'Организация сдающей стороны',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('deliver_name', 'kmhMassByMass.' || 'deliver_name',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('receiver_org', 'kmhMassByMass.' || 'receiver_org',
        'Организация принимающей стороны',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('receiver_name', 'kmhMassByMass.' || 'receiver_name',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('service_org', 'kmhMassByMass.' || 'service_org',
        'Название сервисной организации',
        TRUE, 'KMH_MASSM_BY_MASSM'),
       ('service_name', 'kmhMassByMass.' || 'service_name',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'KMH_MASSM_BY_MASSM');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('delta_max', 'kmhMassByMass.' || 'delta_max',
        '',
        FALSE, 'KMH_MASSM_BY_MASSM'),
       ('conclusion', 'kmhMassByMass.' || 'conclusion',
        'годен/не годен',
        FALSE, 'KMH_MASSM_BY_MASSM');