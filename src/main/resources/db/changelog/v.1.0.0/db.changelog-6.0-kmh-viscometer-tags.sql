--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

--changeset alina.parfenteva:2
INSERT INTO report_type (id, name, description, active)
VALUES ('kmhViscometer', 'КМХ преобразователя вязкости по вискозиметру', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('protocolNumber', 'kmhVisc.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhViscometer'),
       ('siknNumber', 'kmhVisc.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhViscometer'),
       ('place_name', 'kmhVisc.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'kmhViscometer'),

       -- Таблица 1
       ('transmitter_type', 'kmhVisc.' || 'transmitter_type',
        'Тип, марка преобразователя вязкости',
        TRUE, 'kmhViscometer'),
       ('viscometer_type', 'kmhVisc.' || 'viscometer_type',
        'Тип, марка вискозиметра лабораторного', TRUE, 'kmhViscometer'),
       ('transmitter_number', 'kmhVisc.' || 'transmitter_number',
        'Заводской номер преобразователя вязкости',
        TRUE, 'kmhViscometer'),
       ('viscometer_number', 'kmhVisc.' || 'viscometer_number',
        'Заводской номер вискозиметра лабораторного',
        TRUE, 'kmhViscometer'),
       ('transmitter_date', 'kmhVisc.' || 'transmitter_date',
        'Дата последней поверки преобразователя вязкости',
        TRUE, 'kmhViscometer'),
       ('viscometer_date', 'kmhVisc.' || 'viscometer_date', 'Дата последней поверки вискозиметра',
        TRUE, 'kmhViscometer'),
       ('delta_v_PVz', 'kmhVisc.' || 'delta_v_PVz',
        'Предел допустимой абсолютной погрешности определения значения кинематической вязкости нефти в БИК, ΔνПВз, мм2/с (сСт)',
        TRUE, 'kmhViscometer'),
       ('delta_v_il', 'kmhVisc.' || 'delta_v_il',
        'Погрешность метода измерения вязкости нефти по испытательной лаборатории,  Δνил, мм2/с (сСт)',
        TRUE, 'kmhViscometer'),

       -- Таблица 2
       ('t_bik', 'kmhVisc.' || 't_bik',
        'Температура',
        TRUE, 'kmhViscometer'),
       ('ro_bik', 'kmhVisc.' || 'ro_bik',
        'Плотность',
        TRUE, 'kmhViscometer'),
       ('eta_PVz', 'kmhVisc.' || 'eta_PVz',
        'Вязкость',
        TRUE, 'kmhViscometer'),
       ('v_PVz', 'kmhVisc.' || 'v_PVz',
        '',
        TRUE, 'kmhViscometer'),
       ('v_il', 'kmhVisc.' || 'v_il',
        '',
        TRUE, 'kmhViscometer'),

       -- Нижняя часть
       ('deliverOrg', 'kmhVisc.' || 'deliverOrg',
        'Организация сдающей стороны',
        TRUE, 'kmhViscometer'),
       ('metrologistName', 'kmhVisc.' || 'metrologistName',
        'Фамилия И.О. инженера-метролога сдающей стороны',
        TRUE, 'kmhViscometer'),
       ('chemistName', 'kmhVisc.' || 'chemistName',
        'Фамилия И.О. инженера-химика сдающей стороны',
        TRUE, 'kmhViscometer'),
       ('receiverOrg', 'kmhVisc.' || 'receiverOrg',
        'Организация принимающей стороны',
        TRUE, 'kmhViscometer'),
       ('receiverPosition', 'kmhVisc.' || 'receiverPosition',
        'Должность лица от принимающей стороны',
        TRUE, 'kmhViscometer'),
       ('receiverName', 'kmhVisc.' || 'receiverName',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'kmhViscometer'),
       ('serviceOrg', 'kmhVisc.' || 'serviceOrg',
        'Название сервисной организации',
        TRUE, 'kmhViscometer'),
       ('servicePosition', 'kmhVisc.' || 'servicePosition',
        'Должность лица от сервисной организации',
        TRUE, 'kmhViscometer'),
       ('serviceName', 'kmhVisc.' || 'serviceName',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhViscometer');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('v_PVz_minus_v_il', 'kmhVisc.' || 'v_PVz_minus_v_il',
        'Модуль разности v_PVZ и v_il',
        FALSE, 'kmhViscometer'),
       ('conclusion', 'kmhVisc.' || 'conclusion',
        'годен/не годен',
        FALSE, 'kmhViscometer');