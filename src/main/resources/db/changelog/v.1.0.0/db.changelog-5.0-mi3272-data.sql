--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

--changeset alina.parfenteva:2
-- InitialData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('measureCount', 'mi3272.measureCount', 'кол-во измерений (measureCount >=5)', TRUE, 'MI3272'),
       ('seriesCount', 'mi3272.seriesCount', 'количество серий проходов поршня компакт-прувера (seriesCount >=5)', TRUE,
        'MI3272'),
       ('pointsCount', 'mi3272.pointsCount', 'кол-во точек расхода', TRUE, 'MI3272'),
       ('usedTPR', 'mi3272.usedTPR', 'Используется ли ТПР во время поверки (true/false)', TRUE, 'MI3272'),
       ('TPRInKP', 'mi3272.TPRInKP', 'Используется ТПР, входящий в состав компакт-прувера или не входящий (true/false)',
        TRUE, 'MI3272'),
       ('calibrCharImpl', 'mi3272.calibrCharImpl', 'Реализация градуировочных характеристик. Одно из трех значений:
        ""ПЭП"" - реализация в ПЭП,
        ""СОИ рабочий диапазон"" - реализация в СОИ в виде постоянного значения К-фактора,
        ""СОИ поддиапазон"" - реализация в СОИ в виде кусочно-линейной аппроксимации (по умолчанию)', TRUE, 'MI3272'),
       ('operatingFluid', 'mi3272.operatingFluid', 'Группа, к которой принадлежит рабочая жидкость.
    Значения могут быть: ""нефть"", ""бензины"", ""реактивные топлива"", ""нефтяные топлива"".
    С помощью этого параметра вычисляются beta_fluid_ij и gamma_fluid_ij', TRUE, 'MI3272'),
       ('Q_ij_TPR', 'mi3272.Q_ij_TPR', 'значения расхода для определения коэффициентов преобразования ТПР (таблица 2 часть I)
    Если ТПР не используется, то этот параметр не используется', TRUE, 'MI3272'),
       ('N_TPR_ij_avg', 'mi3272.N_TPR_ij_avg',
        'кол-во импульсов, выдаваемых ТПР, усредненное для каждого прохода поршня в серии, имп', TRUE, 'MI3272'),
       ('t_TPR_ij_avg', 'mi3272.t_TPR_ij_avg',
        'температура рабочей жидкости в ТПР, усредненная для каждого прохода поршня в серии, ºC. Записывается только если при поверке используют ТПР, не входящий в состав компакт-прувера',
        TRUE, 'MI3272'),
       ('P_TPR_ij_avg', 'mi3272.P_TPR_ij_avg',
        'давление рабочей жидкости в ТПР, усредненное для каждого прохода поршня в серии, МПа. Записывается только если при поверке используют ТПР, не входящий в состав компакт-прувера',
        TRUE, 'MI3272'),
       ('t_KP_ij_avg', 'mi3272.t_KP_ij_avg', 'температура рабочей жидкости в компакт-прувере, ºC', TRUE, 'MI3272'),
       ('P_KP_ij_avg', 'mi3272.P_KP_ij_avg', 'давление рабочей жидкости в компакт-прувере, Мпа', TRUE, 'MI3272'),
       ('t_st_ij', 'mi3272.t_st_ij',
        'температура стержня, на котором установлены оптические сигнализаторы (детекторы), при i-том измерении в j-ой точке расхода, ºC-1',
        TRUE, 'MI3272'),
       ('V_KP_0', 'mi3272.V_KP_0',
        'вместимость калиброванного участка компакт-прувера согласно свидетельству о поверке', TRUE, 'MI3272'),
       ('alpha_cyl_t', 'mi3272.alpha_cyl_t',
        'коэффициент линейного расширения материала цилиндра компакт-прувера, ºC-1. Значение берут из таблицы Б.1 приложения Б. Если этот коэффициент указан в паспорте или техническом описании на компакт-прувер (или в заводском сертификате калибровки компакт-прувера, то значение нужно брать оттуда. Либо вместо этого коэффициента можно прислать alpha_cyl_t_sq',
        TRUE, 'MI3272'),
       ('alpha_cyl_t_sq', 'mi3272.alpha_cyl_t_sq',
        'квадратный коэффициент расширения цилиндра, ºC-1. Может быть указан в паспорте или техническом описании на компакт-прувер (или в заводском сертификате калибровки компакт-прувера)',
        TRUE, 'MI3272'),
       ('alpha_st_t', 'mi3272.alpha_st_t',
        'коэффициент линейного расширения материала стержня, на котором установлены оптические сигнализаторы (детекторы), ºC-1 (значения берут из таблицы Б.1 приложения Б)',
        TRUE, 'MI3272'),
       ('ro_PP_ij', 'mi3272.ro_PP_ij', 'плотность рабочей жидкости, измеренной поточным ПП, участвующим в поверке',
        TRUE, 'MI3272'),
       ('t_PP_ij', 'mi3272.t_PP_ij', 'температура рабочей жидкости в поточном ПП, ºC', TRUE, 'MI3272'),
       ('P_PP_ij', 'mi3272.P_PP_ij', 'давление рабочей жидкости в поточном ПП, Мпа', TRUE, 'MI3272'),
       ('N2_TPR_ij_avg', 'mi3272.N2_TPR_ij_avg',
        'кол-во импульсов, выдаваемых ТПР, усредненное для каждого прохода поршня в серии, имп. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('t2_KP_ij_avg', 'mi3272.t2_KP_ij_avg',
        'температура рабочей жидкости в компакт-прувере, ºC. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('P2_KP_ij_avg', 'mi3272.P2_KP_ij_avg',
        'давление рабочей жидкости в компакт-прувере, Мпа. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('t2_TPR_ij_avg', 'mi3272.t2_TPR_ij_avg',
        'температура рабочей жидкости в ТПР, усредненная для каждого прохода поршня в серии, ºC. Записывается только если при поверке используют ТПР, не входящий в состав компакт-пруверадля повтороного подсчета коэффициента преобразования ТПР. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('P2_TPR_ij_avg', 'mi3272.P2_TPR_ij_avg',
        'давление рабочей жидкости в ТПР, усредненное для каждого прохода поршня в серии, МПа. Записывается только если при поверке используют ТПР, не входящий в состав компакт-прувера. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('t2_st_ij', 'mi3272.t2_st_ij',
        'температура стержня, на котором установлены оптические сигнализаторы (детекторы), при i-том измерении в j-ой точке расхода, ºC-1. Для повтороного подсчета коэффициента преобразования ТПР',
        TRUE, 'MI3272'),
       ('Q_ij', 'mi3272.Q_ij', 'массовый расход при определении МХ массомера, т/ч', TRUE, 'MI3272'),
       ('N_mas_ij', 'mi3272.N_mas_ij', 'количество импульсов, выданных поверяемым массомером, т', TRUE, 'MI3272'),
       ('N_TPR_ij_zad', 'mi3272.N_TPR_ij_zad', 'количество импульсов для ТПР, имп. Рекомендуется >= 10000', TRUE,
        'MI3272'),
       ('t_TPR_ij', 'mi3272.t_TPR_ij', 'температура рабочей жидкости в ТПР, ºC', TRUE, 'MI3272'),
       ('P_TPR_ij', 'mi3272.P_TPR_ij', 'давление рабочей жидкости в ТПР, Мпа', TRUE, 'MI3272'),
       ('ro_BIK_ij', 'mi3272.ro_BIK_ij',
        'плотность рабочей жидкости, измеренная ПП, установленным в БИК, при i-м измерении в j-ой точке расходе, кг/м3',
        TRUE, 'MI3272'),
       ('KF_conf', 'mi3272.KF_conf', '', TRUE, 'MI3272'),
       ('K_PEP_gr', 'mi3272.K_PEP_gr',
        'градуировочный коэффициент, определенный при предыдущей поверке или заводской калибровке (при выпуске из производства) и установленный в ПЭП',
        TRUE, 'MI3272'),
       ('MF_set_range', 'mi3272.MF_set_range',
        'коэффициент коррекции измерений массы, установленный в ПЭП по результатам предыдущей поверки', TRUE, 'MI3272'),
       ('delta_KP', 'mi3272.delta_KP',
        'пределы относительной погрешности компакт-прувера согласно описани. Типа (или из свидетельства о поверке), %',
        TRUE, 'MI3272'),
       ('delta_PP', 'mi3272.delta_PP',
        'пределы допускаемой относительной погрешности поточного ПП, применяемого при поверке массомера, % (из свидетельства о поверке)',
        TRUE, 'MI3272'),
       ('delta_UOI_K', 'mi3272.delta_UOI_K',
        'пределы допускаемой относительной погрешности УОИ при вычислении К-фактора массомера, % (из описания типа или свидетельства о поверке)',
        TRUE, 'MI3272'),
       ('delta_t_KP', 'mi3272.delta_t_KP',
        'пределы допускаемых абсолюьных погрешностей датчика температуры (или термометров), используемых в процессе поверки для измерений температуры рабочей жидкости в компакт-прувере,  ºC (из действующих свидетельств о поверке)',
        TRUE, 'MI3272'),
       ('delta_t_PP', 'mi3272.delta_t_PP',
        'пределы допускаемых абсолюьных погрешностей датчика температуры (или термометров), используемых в процессе поверки для измерений температуры рабочей жидкости в поточном ПП,  ºC (из действующих свидетельств о поверке)',
        TRUE, 'MI3272'),
       ('ZS', 'mi3272.ZS', 'значение стабильности нуля, т/ч (из описания типа массомера)', TRUE, 'MI3272'),
       ('D', 'mi3272.D',
        'внутренний диаметр калиброванного участка компакт-прувера (значение берут из паспорта или эксплуатационной документации на компакт-прувер), мм',
        TRUE, 'MI3272'),
       ('s', 'mi3272.s',
        'толщина стенок калиброванного участка компакт-прувера (значение берут из паспорта или эксплуатационной документации на компакт-прувер), мм',
        TRUE, 'MI3272'),
       ('E', 'mi3272.E',
        'модуль упругости материала стенок компакт-прувера (значение берут из таблицы Б.1 приложения Б), Мпа', TRUE,
        'MI3272');

--rollback delete from tag where address = 'mi3272.measureCount'
--rollback delete from tag where address = 'mi3272.seriesCount'
--rollback delete from tag where address = 'mi3272.pointsCount'
--rollback delete from tag where address = 'mi3272.usedTPR'
--rollback delete from tag where address = 'mi3272.TPRInKP'
--rollback delete from tag where address = 'mi3272.calibrCharImpl'
--rollback delete from tag where address = 'mi3272.operatingFluid'
--rollback delete from tag where address = 'mi3272.Q_ij_TPR'
--rollback delete from tag where address = 'mi3272.N_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.t_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.P_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.t_KP_ij_avg'
--rollback delete from tag where address = 'mi3272.P_KP_ij_avg'
--rollback delete from tag where address = 'mi3272.t_st_ij'
--rollback delete from tag where address = 'mi3272.V_KP_0'
--rollback delete from tag where address = 'mi3272.alpha_cyl_t'
--rollback delete from tag where address = 'mi3272.alpha_cyl_t_sq'
--rollback delete from tag where address = 'mi3272.alpha_st_t'
--rollback delete from tag where address = 'mi3272.ro_PP_ij'
--rollback delete from tag where address = 'mi3272.t_PP_ij'
--rollback delete from tag where address = 'mi3272.P_PP_ij'
--rollback delete from tag where address = 'mi3272.N2_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.t2_KP_ij_avg'
--rollback delete from tag where address = 'mi3272.P2_KP_ij_avg'
--rollback delete from tag where address = 'mi3272.t2_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.P2_TPR_ij_avg'
--rollback delete from tag where address = 'mi3272.t2_st_ij'
--rollback delete from tag where address = 'mi3272.Q_ij'
--rollback delete from tag where address = 'mi3272.N_mas_ij'
--rollback delete from tag where address = 'mi3272.N_TPR_ij_zad'
--rollback delete from tag where address = 'mi3272.t_TPR_ij'
--rollback delete from tag where address = 'mi3272.P_TPR_ij'
--rollback delete from tag where address = 'mi3272.ro_BIK_ij'
--rollback delete from tag where address = 'mi3272.KF_conf'
--rollback delete from tag where address = 'mi3272.K_PEP_gr'
--rollback delete from tag where address = 'mi3272.MF_set_range'
--rollback delete from tag where address = 'mi3272.delta_KP'

--changeset alina.parfenteva:3
-- InitialTextData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('protocolNumber', 'mi3272.protocolNumber', 'номер протокола поверки', TRUE, 'MI3272'),
       ('massmeterModel', 'mi3272.massmeterModel', 'модель массомера', TRUE, 'MI3272'),
       ('place_name', 'mi3272.place_name', 'место проведения поверки - наименование объекта (ПСП)', TRUE, 'MI3272'),
       ('place_owner', 'mi3272.place_owner', 'место проведения поверки - наименование владельца объекта (ПСП)', TRUE, 'MI3272'),
       ('massmeterSensor', 'mi3272.massmeterSensor', 'модель сенсора поверяемого массомера', TRUE, 'MI3272'),
       ('massmeterDu', 'mi3272.massmeterDu', 'Ду поверяемого массомера', TRUE, 'MI3272'),
       ('massmeterNumber', 'mi3272.massmeterNumber', 'заводской номер поверяемого массомера', TRUE, 'MI3272'),
       ('pepModel', 'mi3272.pepModel', 'модель ПЭП', TRUE, 'MI3272'),
       ('pepNumber', 'mi3272.pepNumber', 'заводской номер ПЭП', TRUE, 'MI3272'),
       ('installedOn', 'mi3272.installedOn', 'установлен на (СИКН, СИКНП, СИКЖУ, СИКНС)', TRUE, 'MI3272'),
       ('ilNumber', 'mi3272.ilNumber', 'ИЛ №', TRUE, 'MI3272'),
       ('cpType', 'mi3272.cpType', 'тип компакт-прувера', TRUE, 'MI3272'),
       ('cpGrade', 'mi3272.cpGrade', 'разряд компакт-прувера', TRUE, 'MI3272'),
       ('cpNumber', 'mi3272.cpNumber', 'заводской номер компакт-прувера', TRUE, 'MI3272'),
       ('cpDate', 'mi3272.cpDate', 'дата поверки компакт-прувера', TRUE, 'MI3272'),
       ('tprType', 'mi3272.tprType', 'тип ТПР', TRUE, 'MI3272'),
       ('tprRange', 'mi3272.tprRange', 'диапазон измерений ТПР', TRUE, 'MI3272'),
       ('tprNumber', 'mi3272.tprNumber', 'заводской номер ТПР', TRUE, 'MI3272'),
       ('ppType', 'mi3272.ppType', 'тип поточного ПП', TRUE, 'MI3272'),
       ('ppNumber', 'mi3272.ppNumber', 'заводской номер поточного ПП', TRUE, 'MI3272'),
       ('ppDate', 'mi3272.ppDate', 'дата поверки поточного ПП', TRUE, 'MI3272'),
       ('companyName', 'mi3272.companyName', 'наименование поверяющей организации', TRUE, 'MI3272'),
       ('verifierName', 'mi3272.verifierName', 'инициалы, фамилия поверителя', TRUE, 'MI3272');

--rollback delete from tag where address = 'mi3272.protocolNumber'
--rollback delete from tag where address = 'mi3272.massmeterModel'
--rollback delete from tag where address = 'mi3272.place_name'
--rollback delete from tag where address = 'mi3272.place_owner'
--rollback delete from tag where address = 'mi3272.massmeterSensor'
--rollback delete from tag where address = 'mi3272.massmeterDu'
--rollback delete from tag where address = 'mi3272.massmeterNumber'
--rollback delete from tag where address = 'mi3272.pepModel'
--rollback delete from tag where address = 'mi3272.pepNumber'
--rollback delete from tag where address = 'mi3272.installedOn'
--rollback delete from tag where address = 'mi3272.ilNumber'
--rollback delete from tag where address = 'mi3272.cpType'
--rollback delete from tag where address = 'mi3272.cpGrade'
--rollback delete from tag where address = 'mi3272.cpNumber'
--rollback delete from tag where address = 'mi3272.cpDate'
--rollback delete from tag where address = 'mi3272.tprType'
--rollback delete from tag where address = 'mi3272.tprRange'
--rollback delete from tag where address = 'mi3272.tprNumber'
--rollback delete from tag where address = 'mi3272.ppType'
--rollback delete from tag where address = 'mi3272.ppNumber'
--rollback delete from tag where address = 'mi3272.ppDate'
--rollback delete from tag where address = 'mi3272.companyName'
--rollback delete from tag where address = 'mi3272.verifierName'

--changeset alina.parfenteva:4
-- FinalData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('beta_fluid_ij', 'mi3272.beta_fluid_ij', 'коэффициент объемного расширения рабочей жидкости при i-ой серии проходов поршня в j-ой точке расхода, ºC-1. Значение определяется по приложению B', FALSE, 'MI3272'),
       ('gamma_fluid_ij', 'mi3272.gamma_fluid_ij', 'коэффициент сжимаемости рабочей жидкости при i-ой серии проходов поршня в j-ой точке расхода, МПа-1. Значение определяется по приложению B', FALSE, 'MI3272'),
       ('K_TPR_ij', 'mi3272.K_TPR_ij', 'коэффициент преобразования ТПР для каждой i-ой серии проходов поршня в j-ой точке расхода, имп/м3', FALSE, 'MI3272'),
       ('V_KP_pr_ij', 'mi3272.V_KP_pr_ij', 'вместимость калиброванного участка', FALSE, 'MI3272'),
       ('Pi_j', 'mi3272.Pi_j', 'повторяемость коэффициентов преобразования ТПР, %', FALSE, 'MI3272'),
       ('K_TPR_j', 'mi3272.K_TPR_j', 'коэффициент преобразования ТПР в j-ой точке расхода, имп/м3', FALSE, 'MI3272'),
       ('K2_TPR_j', 'mi3272.K2_TPR_j', 'коэффициент преобразования ТПР в j-ой точке расхода, расчитанный повторно, имп/м3', FALSE, 'MI3272'),
       ('delta_K_j', 'mi3272.delta_K_j', 'Относитльное отклонение K2_TPR_j от K_TPR_j, %', FALSE, 'MI3272'),
       ('V_TPR_ij', 'mi3272.V_TPR_ij', 'Объем рабочей жидкости, измеренного ТПР, м3', FALSE, 'MI3272'),
       ('ro_PP_pr_ij', 'mi3272.ro_PP_pr_ij', 'плотность рабочей жидкости, измеренная поточным ПП и приведенная к рабочим условиям в ТПР (компакт-прувере)', FALSE, 'MI3272'),
       ('M_re_ij', 'mi3272.M_re_ij', 'масса рабочей жидкости, т. Вычисляется используя результаты измерений рабочих эталонов (ТПР или компакт-прувер и поточный ПП)', FALSE, 'MI3272'),
       ('M_mas_ij', 'mi3272.M_mas_ij', 'масса рабочей жидкости, измеренная поверяемым массомером, т', FALSE, 'MI3272'),
       ('MF_ij', 'mi3272.MF_ij', 'коэффициент коррекции измерений массы (mass-factor)', FALSE, 'MI3272'),
       ('KF_ij', 'mi3272.KF_ij', 'значение K-фактора массомера, определенное для i-того измерения в j-ой точке расхода, имп/т', FALSE, 'MI3272'),
       ('alpha_cyl_t', 'mi3272.alpha_cyl_t_out', 'коэффициент линейного расширения материала цилиндра компакт-прувера, ºC-1', FALSE, 'MI3272'),
       ('t_P_n', 'mi3272.t_P_n', 'квантиль распределения Стьюдента [коэффициент, зависящий от доверительной вероятности P и количества измерений n (n = сум(nj)]', FALSE, 'MI3272'),
       ('Z_P', 'mi3272.Z_P', 'коэффициент, зависящий от доверительной вероятности P и величины соотношения thtea_sigma / S_KF_range', FALSE, 'MI3272'),
       ('Z_P_k', 'mi3272.Z_P_k', 'коэффициент, зависящий от доверительной вероятности P и величины соотношения thtea_sigma / S_KF_range', FALSE, 'MI3272'),
       ('Q_j_avg', 'mi3272.Q_j_avg', 'усредненный массовый расход', FALSE, 'MI3272'),
       ('MForKF_j_avg', 'mi3272.MForKF_j_avg', 'среднее арифметическое значение MF или KF в j-ой точке расхода', FALSE, 'MI3272'),
       ('S_MForKF_range', 'mi3272.S_MForKF_range', 'среднее квадратическое отклонение (СКО) результатов определений MF или KF для точек расхода в рабочем диапазоне', FALSE, 'MI3272'),
       ('delta_mas_0', 'mi3272.delta_mas_0', 'относительная погрешность стабильности нуля', FALSE, 'MI3272'),
       ('MForKF_range', 'mi3272.MForKF_range', 'среднее арифметическое значение MF или KF в рабочем диапазоне расхода', FALSE, 'MI3272'),
       ('K_gr', 'mi3272.K_gr', 'значение градуировочного коэффициента', FALSE, 'MI3272'),
       ('epsilon', 'mi3272.epsilon', 'случайная составляющая погрешности массомера, %', FALSE, 'MI3272'),
       ('theta_sigma', 'mi3272.theta_sigma', 'систематическая составляющая погрешности массомера, %', FALSE, 'MI3272'),
       ('theta_KF_range', 'mi3272.theta_KF_range', 'составляющая систематическая погрешность, %', FALSE, 'MI3272'),
       ('delta', 'mi3272.delta', 'относительная погрешность массомера, %', FALSE, 'MI3272'),
       ('Q_kmin', 'mi3272.Q_kmin', 'минимальное значение расхода в k-ом поддиапазоне', FALSE, 'MI3272'),
       ('Q_kmax', 'mi3272.Q_kmax', 'максимальное значение расхода в k-ом поддиапазоне', FALSE, 'MI3272'),
       ('S_KF_k', 'mi3272.S_KF_k', 'СКО результатов определений K-фактора для точек расхода, если ГХ реализуют в виде кусочно-линейной аппроксимации', FALSE, 'MI3272'),
       ('delta_mas_0k', 'mi3272.delta_mas_0k', 'относиетльная погрешность, %', FALSE, 'MI3272'),
       ('theta_KF_k', 'mi3272.theta_KF_k', 'составляющая систематической погрешности, обусловленная аппроксимацией ГХ массомера в k-том поддиапазоне расхода', FALSE, 'MI3272'),
       ('epsilon_k', 'mi3272.epsilon_k', 'случайная составляющая погрешности массомера, %.', FALSE, 'MI3272'),
       ('theta_sigma_k', 'mi3272.theta_sigma_k', 'систематическая составляющая погрешности массомера, %', FALSE, 'MI3272'),
       ('delta_k', 'mi3272.delta_k', 'относительная погрешность массомера, %', FALSE, 'MI3272'),
       ('usedAs', 'mi3272.usedAs', 'поверяемый массомер годен для использования в качестве рабочего и контрольного или рабочего', FALSE, 'MI3272'),
       ('conclusion', 'mi3272.conclusion', 'годен/не годен', FALSE, 'MI3272');

--rollback delete from tag where address = 'mi3272.beta_fluid_ij' 
--rollback delete from tag where address = 'mi3272.gamma_fluid_ij' 
--rollback delete from tag where address = 'mi3272.K_TPR_ij' 
--rollback delete from tag where address = 'mi3272.V_KP_pr_ij' 
--rollback delete from tag where address = 'mi3272.Pi_j' 
--rollback delete from tag where address = 'mi3272.K_TPR_j' 
--rollback delete from tag where address = 'mi3272.K2_TPR_j' 
--rollback delete from tag where address = 'mi3272.delta_k_j' 
--rollback delete from tag where address = 'mi3272.V_TPR_ij' 
--rollback delete from tag where address = 'mi3272.ro_PP_pr_ij' 
--rollback delete from tag where address = 'mi3272.M_re_ij' 
--rollback delete from tag where address = 'mi3272.M_mas_ij' 
--rollback delete from tag where address = 'mi3272.MF_ij' 
--rollback delete from tag where address = 'mi3272.alpha_cyl_t' 
--rollback delete from tag where address = 'mi3272.t_P_n' 
--rollback delete from tag where address = 'mi3272.Z_P' 
--rollback delete from tag where address = 'mi3272.Z_P_k' 
--rollback delete from tag where address = 'mi3272.Q_j_avg' 
--rollback delete from tag where address = 'mi3272.MForKF_j_avg' 
--rollback delete from tag where address = 'mi3272.S_MForKF_range' 
--rollback delete from tag where address = 'mi3272.delta_mas_0' 
--rollback delete from tag where address = 'mi3272.MForKF_range' 
--rollback delete from tag where address = 'mi3272.K_gr' 
--rollback delete from tag where address = 'mi3272.epsilon' 
--rollback delete from tag where address = 'mi3272.theta_sigma' 
--rollback delete from tag where address = 'mi3272.theta_KF_range' 
--rollback delete from tag where address = 'mi3272.delta' 
--rollback delete from tag where address = 'mi3272.Q_kmin' 
--rollback delete from tag where address = 'mi3272.Q_kmax' 
--rollback delete from tag where address = 'mi3272.S_KF_k' 
--rollback delete from tag where address = 'mi3272.delta_mas_0k' 
--rollback delete from tag where address = 'mi3272.theta_KF_k' 
--rollback delete from tag where address = 'mi3272.epsilon_k' 
--rollback delete from tag where address = 'mi3272.theta_sigma_k' 
--rollback delete from tag where address = 'mi3272.delta_k' 
--rollback delete from tag where address = 'mi3272.usedAs' 
--rollback delete from tag where address = 'mi3272.conclusion' 


-- Флаг, сигнализирующий о начале и конце поверки
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('isFinished', 'mi3272.isFinished',
        'Флаг, сигнализирующий, что поверка завершена. При старте поверки в него записывается значение false, а после окончания - true',
        FALSE, 'MI3272');

--rollback delete from tag where address = 'mi3272.isFinished' 