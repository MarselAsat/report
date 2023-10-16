--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('kmhMoistureMeter', 'КМХ рабочего и резервного влагомеров по лабораторным измерениям', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('protocolNumber', 'kmhMoistMet.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhMoistureMeter'),
       ('siknNumber', 'kmhMoistMet.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhMoistureMeter'),
       ('place_name', 'kmhMoistMet.' || 'place_name',
        'Место проведения КМХ',
        TRUE, 'kmhMoistureMeter'),

       -- Таблица 1
       ('reserve_mm_type', 'kmhMoistMet.' || 'reserve_mm_type',
        'Тип, марка резервного влагомера',
        TRUE, 'kmhMoistureMeter'),
       ('working_mm_type', 'kmhMoistMet.' || 'working_mm_type',
        'Тип, марка рабочего влагомера', TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_number', 'kmhMoistMet.' || 'reserve_mm_number',
        'Заводской номер резервного влагомера',
        TRUE, 'kmhMoistureMeter'),
       ('working_mm_number', 'kmhMoistMet.' || 'working_mm_number',
        'Заводской номер рабочего влагомера',
        TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_date', 'kmhMoistMet.' || 'reserve_mm_date',
        'Дата последней поверки резервного влагомера',
        TRUE, 'kmhMoistureMeter'),
       ('working_mm_date', 'kmhMoistMet.' || 'working_mm_date', 'Дата последней поверки рабочего влагомера',
        TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_delta_W_kv', 'kmhMoistMet.' || 'reserve_mm_delta_W_kv',
        'Предел абсолютной погрешности измерений массовой доли воды в нефти резервным влагомером, ΔWкв %',
        TRUE, 'kmhMoistureMeter'),
       ('working_mm_delta_W_kv', 'kmhMoistMet.' || 'working_mm_delta_W_kv',
        'Предел абсолютной погрешности измерений массовой доли воды в нефти рабочим влагомером, ΔWкв %',
        TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_delta_W_0', 'kmhMoistMet.' || 'reserve_mm_delta_W_0',
        'Предел допускаемой абсолютной погрешности определения доли воды в ХАЛ резервным влагомером по ГОСТ 2477, ΔW0 %',
        TRUE, 'kmhMoistureMeter'),
       ('working_mm_delta_W_0', 'kmhMoistMet.' || 'working_mm_delta_W_0',
        'Предел допускаемой абсолютной погрешности определения доли воды в ХАЛ рабочим влагомером по ГОСТ 2477, ΔW0 %',
        TRUE, 'kmhMoistureMeter'),

       -- Таблица 2
       ('measure_time', 'kmhMoistMet.' || 'measure_time',
        'Время проведения замеров (московское)',
        TRUE, 'kmhMoistureMeter'),
       ('work_mm_phi_v', 'kmhMoistMet.' || 'work_mm_phi_v',
        'Значение влагосодержания φв по рабочему влагомеру,  (% объемных)',
        TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_phi_v', 'kmhMoistMet.' || 'reserve_mm_phi_v',
        'Значение влагосодержания φв по резервному влагомеру,  (% объемных)',
        TRUE, 'kmhMoistureMeter'),
       ('work_mm_W_kv', 'kmhMoistMet.' || 'work_mm_W_kv',
        'Значение влагосодержания Wкв по рабочему влагомеру, (% массовых)',
        TRUE, 'kmhMoistureMeter'),
       ('reserve_mm_W_kv', 'kmhMoistMet.' || 'reserve_mm_W_kv',
        'Значение влагосодержания Wкв по резервному влагомеру, (% массовых)',
        TRUE, 'kmhMoistureMeter'),
       ('W_0', 'kmhMoistMet.' || 'W_0',
        'Значение влагосодержания W0, по анализам ХАЛ, (% массовых)',
        TRUE, 'kmhMoistureMeter'),
       ('rho_v', 'kmhMoistMet.' || 'rho_v',
        'Плотность нефти по СИКН, ρv, кг/м3',
        TRUE, 'kmhMoistureMeter'),

       -- Нижняя часть
       ('deliverOrg', 'kmhMoistMet.' || 'deliverOrg',
        'Организация сдающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('metrologistName', 'kmhMoistMet.' || 'metrologistName',
        'Фамилия И.О. инженера-метролога сдающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('chemistName', 'kmhMoistMet.' || 'chemistName',
        'Фамилия И.О. инженера-химика сдающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('receiverOrg', 'kmhMoistMet.' || 'receiverOrg',
        'Организация принимающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('receiverPosition', 'kmhMoistMet.' || 'receiverPosition',
        'Должность лица от принимающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('receiverName', 'kmhMoistMet.' || 'receiverName',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'kmhMoistureMeter'),
       ('serviceOrg', 'kmhMoistMet.' || 'serviceOrg',
        'Название сервисной организации',
        TRUE, 'kmhMoistureMeter'),
       ('servicePosition', 'kmhMoistMet.' || 'servicePosition',
        'Должность лица от сервисной организации',
        TRUE, 'kmhMoistureMeter'),
       ('serviceName', 'kmhMoistMet.' || 'serviceName',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhMoistureMeter');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('work_mm_W_id', 'kmhMoistMet.' || 'work_mm_W_id',
        'Расхождение результатов измерений для рабочего влагомера Ŵid =|Wкв –W0|, (%)',
        FALSE, 'kmhMoistureMeter'),
       ('reserve_mm_W_id', 'kmhMoistMet.' || 'reserve_mm_W_id',
        'Расхождение результатов измерений для резервного влагомера Ŵid =|Wкв –W0|, (%)',
        FALSE, 'kmhMoistureMeter'),
       ('working_mm_conclusion', 'kmhMoistMet.' || 'working_mm_conclusion',
        'годен/не годен для рабочего влагомера',
        FALSE, 'kmhMoistureMeter'),
       ('reserve_mm_conclusion', 'kmhMoistMet.' || 'reserve_mm_conclusion',
        'годен/не годен для резервного влагомера',
        FALSE, 'kmhMoistureMeter');