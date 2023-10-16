--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('kmhMassmByMassm', 'КМХ рабочего МПР по контрольному', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('protocolNumber', 'kmhMassByMass.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhMassmByMassm'),
       ('ilNumber', 'kmhMassByMass.' || 'ilNumber',
        'Номер измерительной линии',
        TRUE, 'kmhMassmByMassm'),
       ('siknNumber', 'kmhMassByMass.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhMassmByMassm'),
       ('place_name', 'kmhMassByMass.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'kmhMassmByMassm'),

       -- Таблица 1
       ('work_mpr_sensor_type', 'kmhMassByMass.' || 'work_mpr_sensor_type',
        'Тип, марка сенсора рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('work_mpr_pep_type', 'kmhMassByMass.' || 'work_mpr_pep_type',
        'Тип, марка ПЭП рабочего МПР', TRUE, 'kmhMassmByMassm'),

       ('contr_mpr_sensor_type', 'kmhMassByMass.' || 'contr_mpr_sensor_type',
        'Тип, марка сенсора контрольного МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_pep_type', 'kmhMassByMass.' || 'contr_mpr_pep_type',
        'Тип, марка ПЭП контрольного МПР', TRUE, 'kmhMassmByMassm'),

       ('work_mpr_sensor_number', 'kmhMassByMass.' || 'work_mpr_sensor_number',
        'Заводской номер сенсора рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('work_mpr_pep_number', 'kmhMassByMass.' || 'work_mpr_pep_number',
        'Заводской номер ПЭП рабочего МПР', TRUE, 'kmhMassmByMassm'),

       ('contr_mpr_sensor_number', 'kmhMassByMass.' || 'contr_mpr_sensor_number',
        'Заводской номер сенсора контрольного МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_pep_number', 'kmhMassByMass.' || 'contr_mpr_pep_number',
        'Заводской номер ПЭП контрольного МПР', TRUE, 'kmhMassmByMassm'),

       ('work_mpr_date', 'kmhMassByMass.' || 'work_mpr_date',
        'Дата последней поверки рабочего МПР',
        TRUE, 'kmhMassmByMassm'),
       ('contr_mpr_date', 'kmhMassByMass.' || 'contr_mpr_date',
        'Дата последней поверки контрольного МПР', TRUE, 'kmhMassmByMassm'),

       -- Таблица 2
       ('mass', 'kmhMassByMass.' || 'mass',
        'Заданная масса, т',
        TRUE, 'kmhMassmByMassm'),
       ('Q', 'kmhMassByMass.' || 'Q',
        'Расход, т/ч',
        TRUE, 'kmhMassmByMassm'),
       ('M_il', 'kmhMassByMass.' || 'M_il',
        '',
        TRUE, 'kmhMassmByMassm'),
       ('M_ilk', 'kmhMassByMass.' || 'M_ilk',
        '',
        TRUE, 'kmhMassmByMassm'),
       ('delta_M', 'kmhMassByMass.' || 'delta_M',
        '',
        TRUE, 'kmhMassmByMassm'),

       -- Нижняя часть
       ('deliver_org', 'kmhMassByMass.' || 'deliver_org',
        'Организация сдающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('deliver_name', 'kmhMassByMass.' || 'deliver_name',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('receiver_org', 'kmhMassByMass.' || 'receiver_org',
        'Организация принимающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('receiver_name', 'kmhMassByMass.' || 'receiver_name',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'kmhMassmByMassm'),
       ('service_org', 'kmhMassByMass.' || 'service_org',
        'Название сервисной организации',
        TRUE, 'kmhMassmByMassm'),
       ('service_name', 'kmhMassByMass.' || 'service_name',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhMassmByMassm');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('delta_max', 'kmhMassByMass.' || 'delta_max',
        '',
        FALSE, 'kmhMassmByMassm'),
       ('conclusion', 'kmhMassByMass.' || 'conclusion',
        'годен/не годен',
        FALSE, 'kmhMassmByMassm');