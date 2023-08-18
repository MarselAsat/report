--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

--changeset alina.parfenteva:2
-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('protocolNumber', 'kmhVisc.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'KMH_VISCOMETER'),
       ('siknNumber', 'kmhVisc.' || 'siknNumber',
        'СИКН №',
        TRUE, 'KMH_VISCOMETER'),
       ('place_name', 'kmhVisc.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'KMH_VISCOMETER'),

       -- Таблица 1
       ('transmitter_type', 'kmhVisc.' || 'transmitter_type',
        'Тип, марка преобразователя вязкости',
        TRUE, 'KMH_VISCOMETER'),
       ('viscometer_type', 'kmhVisc.' || 'viscometer_type',
        'Тип, марка вискозиметра лабораторного', TRUE, 'KMH_VISCOMETER'),
       ('transmitter_number', 'kmhVisc.' || 'transmitter_number',
        'Заводской номер преобразователя вязкости',
        TRUE, 'KMH_VISCOMETER'),
       ('viscometer_number', 'kmhVisc.' || 'viscometer_number',
        'Заводской номер вискозиметра лабораторного',
        TRUE, 'KMH_VISCOMETER'),
       ('transmitter_date', 'kmhVisc.' || 'transmitter_date',
        'Дата последней поверки преобразователя вязкости',
        TRUE, 'KMH_VISCOMETER'),
       ('viscometer_date', 'kmhVisc.' || 'viscometer_date', 'Дата последней поверки вискозиметра',
        TRUE, 'KMH_VISCOMETER'),
       ('delta_v_PVz', 'kmhVisc.' || 'delta_v_PVz',
        'Предел допустимой абсолютной погрешности определения значения кинематической вязкости нефти в БИК, ΔνПВз, мм2/с (сСт)',
        TRUE, 'KMH_VISCOMETER'),
       ('delta_v_il', 'kmhVisc.' || 'delta_v_il',
        'Погрешность метода измерения вязкости нефти по испытательной лаборатории,  Δνил, мм2/с (сСт)',
        TRUE, 'KMH_VISCOMETER'),

       -- Таблица 2
       ('t_bik', 'kmhVisc.' || 't_bik',
        'Температура',
        TRUE, 'KMH_VISCOMETER'),
       ('ro_bik', 'kmhVisc.' || 'ro_bik',
        'Плотность',
        TRUE, 'KMH_VISCOMETER'),
       ('eta_PVz', 'kmhVisc.' || 'eta_PVz',
        'Вязкость',
        TRUE, 'KMH_VISCOMETER'),
       ('v_PVz', 'kmhVisc.' || 'v_PVz',
        '',
        TRUE, 'KMH_VISCOMETER'),
       ('v_il', 'kmhVisc.' || 'v_il',
        '',
        TRUE, 'KMH_VISCOMETER'),

       -- Нижняя часть
       ('deliverOrg', 'kmhVisc.' || 'deliverOrg',
        'Организация сдающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('metrologistName', 'kmhVisc.' || 'metrologistName',
        'Фамилия И.О. инженера-метролога сдающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('chemistName', 'kmhVisc.' || 'chemistName',
        'Фамилия И.О. инженера-химика сдающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('receiverOrg', 'kmhVisc.' || 'receiverOrg',
        'Организация принимающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('receiverPosition', 'kmhVisc.' || 'receiverPosition',
        'Должность лица от принимающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('receiverName', 'kmhVisc.' || 'receiverName',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'KMH_VISCOMETER'),
       ('serviceOrg', 'kmhVisc.' || 'serviceOrg',
        'Название сервисной организации',
        TRUE, 'KMH_VISCOMETER'),
       ('servicePosition', 'kmhVisc.' || 'servicePosition',
        'Должность лица от сервисной организации',
        TRUE, 'KMH_VISCOMETER'),
       ('serviceName', 'kmhVisc.' || 'serviceName',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'KMH_VISCOMETER');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('v_PVz_minus_v_il', 'kmhVisc.' || 'v_PVz_minus_v_il',
        'Модуль разности v_PVZ и v_il',
        FALSE, 'KMH_VISCOMETER'),
       ('conclusion', 'kmhVisc.' || 'conclusion',
        'годен/не годен',
        FALSE, 'KMH_VISCOMETER');