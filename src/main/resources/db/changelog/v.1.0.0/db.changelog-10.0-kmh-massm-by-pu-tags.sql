--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('kmhMassmByPu', 'КМХ контрольного МПР с помощью ПУ', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('pointsCount', 'kmhMassmByPu.' || 'pointsCount',
        'количество точек расхода',
        TRUE, 'kmhMassmByPu'),
       ('measureCount', 'kmhMassmByPu.' || 'measureCount',
        'количество измерений в точке',
        TRUE, 'kmhMassmByPu'),
    ('deliver_org', 'kmhMassmByPu.' || 'deliver_org',
        'Наименование организации сдаюей стороны',
        TRUE, 'kmhMassmByPu'),
       ('siknNumber', 'kmhMassmByPu.' || 'siknNumber',
        'СИКН №',
        TRUE, 'kmhMassmByPu'),
       ('protocolNumber', 'kmhMassmByPu.' || 'protocolNumber',
        'Номер протокола',
        TRUE, 'kmhMassmByPu'),
       ('il_number_title', 'kmhMassmByPu.' || 'il_number_title',
        'ИЛ № (в заголовке)',
        TRUE, 'kmhMassmByPu'),

       ('place_name', 'kmhMassmByPu.place_name', 'место проведения контроля МХ - наименование объекта (ПСП)', TRUE,
        'kmhMassmByPu'),
       ('place_owner', 'kmhMassmByPu.place_owner',
        'место проведения контроля МХ - наименование владельца объекта (ПСП)', TRUE, 'kmhMassmByPu'),
       ('massmeterSensor', 'kmhMassmByPu.massmeterSensor', 'модель сенсора массомера', TRUE, 'kmhMassmByPu'),
       ('massmeterDu', 'kmhMassmByPu.massmeterDu', 'Ду массомера', TRUE, 'kmhMassmByPu'),
       ('massmeterNumber', 'kmhMassmByPu.massmeterNumber', 'заводской номер массомера', TRUE, 'kmhMassmByPu'),
       ('pepModel', 'kmhMassmByPu.pepModel', 'модель ПЭП', TRUE, 'kmhMassmByPu'),
       ('pepNumber', 'kmhMassmByPu.pepNumber', 'заводской номер ПЭП', TRUE, 'kmhMassmByPu'),
       ('installedOn', 'kmhMassmByPu.installedOn', 'установлен на (СИКН, СИКНП, СИКЖУ, СИКНС)', TRUE,
        'kmhMassmByPu'),
       ('ilNumber', 'kmhMassmByPu.' || 'ilNumber',
        'Номер измерительной линии',
        TRUE, 'kmhMassmByPu'),
       ('operatingFluid', 'kmhMassmByPu.' || 'operatingFluid',
        'Рабочая жидкость',
        TRUE, 'kmhMassmByPu'),
       ('cpType', 'kmhMassmByPu.cpType', 'тип компакт-прувера', TRUE, 'kmhMassmByPu'),
       ('cpGrade', 'kmhMassmByPu.cpGrade', 'разряд компакт-прувера', TRUE, 'kmhMassmByPu'),
       ('cpNumber', 'kmhMassmByPu.cpNumber', 'заводской номер компакт-прувера', TRUE, 'kmhMassmByPu'),
       ('cpDate', 'kmhMassmByPu.cpDate', 'дата поверки компакт-прувера', TRUE, 'kmhMassmByPu'),
       ('tprType', 'kmhMassmByPu.tprType', 'тип ТПР', TRUE, 'kmhMassmByPu'),
       ('tprRange', 'kmhMassmByPu.tprRange', 'диапазон измерений ТПР', TRUE, 'kmhMassmByPu'),
       ('tprNumber', 'kmhMassmByPu.tprNumber', 'заводской номер ТПР', TRUE, 'kmhMassmByPu'),
       ('ppType', 'kmhMassmByPu.ppType', 'тип поточного ПП', TRUE, 'kmhMassmByPu'),
       ('ppNumber', 'kmhMassmByPu.ppNumber', 'заводской номер поточного ПП', TRUE, 'kmhMassmByPu'),
       ('ppDate', 'kmhMassmByPu.ppDate', 'дата поверки поточного ПП', TRUE, 'kmhMassmByPu'),


-- Таблица 1
       ('V_KP_0', 'kmhMassmByPu.' || 'V_KP_0',
        'вместимость калиброванного участка компакт-прувера согласно свидетельству о поверке',
        TRUE, 'kmhMassmByPu'),
       ('delta_KP', 'kmhMassmByPu.' || 'delta_KP',
        'пределы относительной погрешности компакт-прувера согласно описани. Типа (или из свидетельства о поверке), %',
        TRUE, 'kmhMassmByPu'),

       ('D', 'kmhMassmByPu.' || 'D',
        'внутренний диаметр калиброванного участка компакт-прувера (значение берут из паспорта или эксплуатационной документации на компакт-прувер), мм',
        TRUE, 'kmhMassmByPu'),
       ('E', 'kmhMassmByPu.' || 'E',
        'модуль упругости материала стенок компакт-прувера (значение берут из таблицы Б.1 приложения Б), Мпа', TRUE,
        'kmhMassmByPu'),

       ('s', 'kmhMassmByPu.' || 's',
        'толщина стенок калиброванного участка компакт-прувера (значение берут из паспорта или эксплуатационной документации на компакт-прувер), мм',
        TRUE, 'kmhMassmByPu'),
       ('delta_t_KP', 'kmhMassmByPu.' || 'delta_t_KP',
        'пределы допускаемых абсолюьных погрешностей датчика температуры (или термометров), используемых в процессе поверки для измерений температуры рабочей жидкости в компакт-прувере,  ºC (из действующих свидетельств о поверке)',
        TRUE, 'kmhMassmByPu'),

       ('delta_PP', 'kmhMassmByPu.' || 'delta_PP',
        'пределы допускаемой относительной погрешности поточного ПП, применяемого при поверке массомера, % (из свидетельства о поверке)',
        TRUE, 'kmhMassmByPu'),
       ('delta_t_PP', 'kmhMassmByPu.' || 'delta_t_PP',
        'пределы допускаемых абсолюьных погрешностей датчика температуры (или термометров), используемых в процессе поверки для измерений температуры рабочей жидкости в поточном ПП,  ºC (из действующих свидетельств о поверке)',
        TRUE, 'kmhMassmByPu'),

       ('delta_UOI_K', 'kmhMassmByPu.' || 'delta_UOI_K',
        'пределы допускаемой относительной погрешности УОИ при вычислении К-фактора массомера, % (из описания типа или свидетельства о поверке)',
        TRUE, 'kmhMassmByPu'),
       ('KF_conf', 'kmhMassmByPu.' || 'KF_conf',
        '', TRUE, 'kmhMassmByPu'),
       ('ZS', 'kmhMassmByPu.' || 'ZS',
        'значение стабильности нуля, т/ч (из описания типа массомера)', TRUE, 'kmhMassmByPu'),
       ('alpha_cyl_t', 'kmhMassmByPu.' || 'alpha_cyl_t',
        '', TRUE, 'kmhMassmByPu'),
       ('alpha_cyl_t_sq', 'kmhMassmByPu.' || 'alpha_cyl_t_sq',
        '', TRUE, 'kmhMassmByPu'),
       ('alpha_st_t', 'kmhMassmByPu.' || 'alpha_st_t',
        '', TRUE, 'kmhMassmByPu'),

       -- Таблица 2
       ('Q_TPR_ij', 'kmhMassmByPu.' || 'Q_TPR_ij',
        'Расход для вычисления коэффициента ТПР',
        TRUE, 'kmhMassmByPu'),
       ('N_TPR_ij_avg', 'kmhMassmByPu.' || 'N_TPR_ij_avg',
        '',
        TRUE, 'kmhMassmByPu'),
       ('t_TPR_ij_avg', 'kmhMassmByPu.' || 't_TPR_ij_avg',
        '',
        TRUE, 'kmhMassmByPu'),
       ('P_TPR_ij_avg', 'kmhMassmByPu.' || 'P_TPR_ij_avg',
        '',
        TRUE, 'kmhMassmByPu'),
       ('t_KP_ij_avg', 'kmhMassmByPu.' || 't_KP_ij_avg',
        '',
        TRUE, 'kmhMassmByPu'),
       ('P_KP_ij_avg', 'kmhMassmByPu.' || 'P_KP_ij_avg',
        '',
        TRUE, 'kmhMassmByPu'),
       ('t_st_ij', 'kmhMassmByPu.' || 't_st_ij',
        '',
        TRUE, 'kmhMassmByPu'),

-- Заданная масса
       ('mass', 'kmhMassmByPu.' || 'mass',
        'Заданная масса, т',
        TRUE, 'kmhMassmByPu'),

       -- Таблица 3
       ('Q_ij', 'kmhMassmByPu.' || 'Q_ij',
        'Расход для определения МХ массомера',
        TRUE, 'kmhMassmByPu'),
       ('t_il', 'kmhMassmByPu.' || 't_il',
        '',
        TRUE, 'kmhMassmByPu'),
       ('P_il', 'kmhMassmByPu.' || 'P_il',
        '',
        TRUE, 'kmhMassmByPu'),
       ('t_TPR', 'kmhMassmByPu.' || 't_TPR',
        '',
        TRUE, 'kmhMassmByPu'),
       ('P_TPR', 'kmhMassmByPu.' || 'P_TPR',
        '',
        TRUE, 'kmhMassmByPu'),
       ('rho_TPR', 'kmhMassmByPu.' || 'rho_TPR',
        '',
        TRUE, 'kmhMassmByPu'),
       ('M_il', 'kmhMassmByPu.' || 'M_il',
        '',
        TRUE, 'kmhMassmByPu'),
       ('M_TPR', 'kmhMassmByPu.' || 'M_TPR',
        '',
        TRUE, 'kmhMassmByPu'),
       ('delta_M', 'kmhMassmByPu.' || 'delta_M',
        '',
        TRUE, 'kmhMassmByPu'),

       -- Нижняя часть
       ('deliver_name', 'kmhMassmByPu.' || 'deliver_name',
        'Фамилия И.О. лица от сдающей стороны',
        TRUE, 'kmhMassmByPu'),
       ('receiver_org', 'kmhMassmByPu.' || 'receiver_org',
        'Организация принимающей стороны',
        TRUE, 'kmhMassmByPu'),
       ('receiver_name', 'kmhMassmByPu.' || 'receiver_name',
        'Фамилия И.О. лица от принимающей стороны',
        TRUE, 'kmhMassmByPu'),
       ('service_org', 'kmhMassmByPu.' || 'service_org',
        'Название сервисной организации',
        TRUE, 'kmhMassmByPu'),
       ('service_name', 'kmhMassmByPu.' || 'service_name',
        'Фамилия И.О. лица от сервисной организации',
        TRUE, 'kmhMassmByPu');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('V_KP_pr_ij', 'kmhMassmByPu.' || 'V_KP_pr_ij',
        'вместимость калиброванного участка',
        FALSE, 'kmhMassmByPu'),
       ('K_TPR_ij', 'kmhMassmByPu.' || 'K_TPR_ij',
        'коэффициент преобразования ТПР для каждой i-ой серии проходов поршня в j-ой точке расхода, имп/м3',
        FALSE, 'kmhMassmByPu'),
       ('Pi_j', 'kmhMassmByPu.' || 'Pi_j',
        'повторяемость коэффициентов преобразования ТПР, %',
        FALSE, 'kmhMassmByPu'),
       ('K_TPR_j', 'kmhMassmByPu.' || 'K_TPR_j',
        'коэффициент преобразования ТПР в j-ой точке расхода, имп/м3',
        FALSE, 'kmhMassmByPu'),
       ('delta_max', 'kmhMassmByPu.' || 'delta_max',
        '',
        FALSE, 'kmhMassmByPu'),
       ('conclusion', 'kmhMassmByPu.' || 'conclusion',
        'годен/не годен',
        FALSE, 'kmhMassmByPu');