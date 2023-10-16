--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('kmhDensityMeter', 'КМХ ПП по ареометру', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('workingOrReserve', 'kmhDensMet.' || 'workingOrReserve',
        'КМХ по рабочему или резервному ПП. Может иметь только 2 значения: "рабочий" или "резервный"',
        TRUE, 'kmhDensityMeter'),
       ('protocolNumber', 'kmhDensMet.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhDensityMeter'),
       ('siknNumber', 'kmhDensMet.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhDensityMeter'),
       ('place_name', 'kmhDensMet.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'kmhDensityMeter'),

       -- Таблица 1
       ('pp_type', 'kmhDensMet.' || 'pp_type',
        'Тип, марка ПП',
        TRUE, 'kmhDensityMeter'),
       ('areometer_type', 'kmhDensMet.' || 'areometer_type',
        'Тип, марка ареометра', TRUE, 'kmhDensityMeter'),
       ('pp_number', 'kmhDensMet.' || 'pp_number',
        'Заводской номер ПП',
        TRUE, 'kmhDensityMeter'),
       ('areometer_number', 'kmhDensMet.' || 'areometer_number',
        'Заводской номер ареометра',
        TRUE, 'kmhDensityMeter'),
       ('pp_date', 'kmhDensMet.' || 'pp_date',
        'Дата последней поверки ПП',
        TRUE, 'kmhDensityMeter'),
       ('areometer_date', 'kmhDensMet.' || 'areometer_date', 'Дата последней поверки ареометра',
        TRUE, 'kmhDensityMeter'),
       ('delta_pp', 'kmhDensMet.' || 'delta_pp',
        'Предел допускаемой абсолютной погрешности ПП (Δ, кг/м3 )',
        TRUE, 'kmhDensityMeter'),
       ('delta_areometer', 'kmhDensMet.' || 'delta_areometer',
        'Предел допускаемой абсолютной погрешности ареометра (Δ, кг/м3 )',
        TRUE, 'kmhDensityMeter'),
       ('delta_syst', 'kmhDensMet.' || 'delta_syst',
        'Систематическая  погрешность метода определения плотности нефти ареометром (обязательно со знаком «+» или «-»)  -  из  свидетельства о метрологической аттестации МВИ плотности на данную СИКН, (Δ сист , кг/м3 )',
        TRUE, 'kmhDensityMeter'),
       ('delta_met', 'kmhDensMet.' || 'delta_met',
        'Погрешность метода определения плотности нефти ареометром (без знака) -  из  свидетельства о метрологической аттестации МВИ плотности на данную СИКН, (Δ мет , кг/м3)',
        TRUE, 'kmhDensityMeter'),

       -- Таблица 2
       ('Q_i', 'kmhDensMet.' || 'Q_i',
        '',
        TRUE, 'kmhDensityMeter'),
       ('t_pl_i', 'kmhDensMet.' || 't_pl_i',
        '',
        TRUE, 'kmhDensityMeter'),
       ('P_pl_i', 'kmhDensMet.' || 'P_pl_i',
        '',
        TRUE, 'kmhDensityMeter'),
       ('rho_pl_i', 'kmhDensMet.' || 'rho_pl_i',
        '',
        TRUE, 'kmhDensityMeter'),
       ('rho_meas_i', 'kmhDensMet.' || 'rho_meas_i',
        '',
        TRUE, 'kmhDensityMeter'),
       ('t_ar_i', 'kmhDensMet.' || 't_ar_i',
        '',
        TRUE, 'kmhDensityMeter'),

       -- Нижняя часть
       ('deliverOrg', 'kmhDensMet.' || 'deliverOrg',
        'Организация сдающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('metrologistName', 'kmhDensMet.' || 'metrologistName',
        'Фамилия И.О. инженера-метролога сдающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('chemistName', 'kmhDensMet.' || 'chemistName',
        'Фамилия И.О. инженера-химика сдающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('receiverOrg', 'kmhDensMet.' || 'receiverOrg',
        'Организация принимающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('receiverPosition', 'kmhDensMet.' || 'receiverPosition',
        'Должность лица от принимающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('receiverName', 'kmhDensMet.' || 'receiverName',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'kmhDensityMeter'),
       ('serviceOrg', 'kmhDensMet.' || 'serviceOrg',
        'Название сервисной организации',
        TRUE, 'kmhDensityMeter'),
       ('servicePosition', 'kmhDensMet.' || 'servicePosition',
        'Должность лица от сервисной организации',
        TRUE, 'kmhDensityMeter'),
       ('serviceName', 'kmhDensMet.' || 'serviceName',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhDensityMeter');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('beta15', 'kmhDensMet.' || 'beta15',
        '',
        FALSE, 'kmhDensityMeter'),
       ('gamma_pl_i', 'kmhDensMet.' || 'gamma_pl_i',
        '',
        FALSE, 'kmhDensityMeter'),
       ('rho_lpr_i', 'kmhDensMet.' || 'rho_lpr_i',
        '',
        FALSE, 'kmhDensityMeter'),
       ('delta_pk_i', 'kmhDensMet.' || 'delta_pk_i',
        '',
        FALSE, 'kmhDensityMeter'),
       ('conclusion', 'kmhDensMet.' || 'conclusion',
        'годен/не годен',
        FALSE, 'kmhDensityMeter');

--rollback delete from tag where report_type_id = 'kmhDensityMeter'
--rollback delete from report_type where id = 'kmhDensityMeter'