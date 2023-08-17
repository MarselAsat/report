--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

--changeset alina.parfenteva:2
-- InitialData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('Q_ij', 'WinCC_OA.mi3622.' || 'Q_ij', 'значение расхода, т/ч', TRUE, 'MI3622'),
       ('N_e_ij', 'WinCC_OA.mi3622.' || 'N_e_ij', 'кол-во импульсов, поступившее с ПР, имп', TRUE, 'MI3622'),
       ('N_p_ij', 'WinCC_OA.mi3622.' || 'N_p_ij', 'кол-во импульсов, поступившее с поверяемого СРМ, имп', TRUE,
        'MI3622'),
       ('T_ij', 'WinCC_OA.mi3622.' || 'T_ij', 'время измерения, с', TRUE, 'MI3622'),
       ('M_ij', 'WinCC_OA.mi3622.' || 'M_ij', 'масса измеряемой среды, измеренная поверяемым СРМ, т', TRUE,
        'MI3622'),
       ('f_p_max', 'WinCC_OA.mi3622.' || 'f_p_max', 'максимальное значение частоты, установленное в СРМ, Гц', TRUE,
        'MI3622'),
       ('Q_p_max', 'WinCC_OA.mi3622.' || 'Q_p_max', 'максимальное значение расхода, т/ч', TRUE, 'MI3622'),
       ('MF_p', 'WinCC_OA.mi3622.' || 'MF_p',
        'коэффициент коррекции поверяемого СРМ, введенный в измерительный преобразователь по результатам предыдущей поверки',
        TRUE, 'MI3622'),
       ('K_e_arr', 'WinCC_OA.mi3622.' || 'K_e_arr', 'коэффициент градуировочной характеристики ПР, имп/т (при MFэ=1)',
        TRUE, 'MI3622'),
       ('ZS', 'WinCC_OA.mi3622.' || 'ZS',
        'коэффициент коррекции поверяемого СРМ (т/ч), введенный в измерительный преобразователь по результатам предыдущей поверки',
        TRUE, 'MI3622'),
       ('theta_e', 'WinCC_OA.mi3622.' || 'theta_e',
        'граница составляющей неисключенной систематической погрешности, обусловленной погрешностью установки поверочной, % (из свидетельства о поверке установки поверочной)',
        TRUE, 'MI3622'),
       ('theta_t', 'WinCC_OA.mi3622.' || 'theta_t',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием отклонения температуры измеряемой среды в условиях эксплуатации поверяемого СРМ от температуры измеряемой среды при поверке, % (вычисляют, используя данные из технической документации изготовителя)',
        TRUE, 'MI3622'),
       ('theta_p', 'WinCC_OA.mi3622.' || 'theta_p',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием отклонения температуры измеряемой среды в условиях эксплуатации поверяемого СРМ от температуры измеряемой среды при поверке, % (вычисляют, используя данные из технической документации изготовителя)',
        TRUE, 'MI3622'),
       ('theta_N', 'WinCC_OA.mi3622.' || 'theta_N',
        'граница составляющей неисключенной систематической погрешности, обусловленной погрешностью СОИ, %; принимают равной пределам допускаемой относительной погрешности преобразования входных электрических сигналов в значение коэффициентов коррекции (преобразования) в СОИ, % (из свидетельства о поверке СОИ)',
        TRUE, 'MI3622'),
       ('theta_PDt', 'WinCC_OA.mi3622.' || 'theta_PDt',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием максимального отклонения температуры измеряемой среды при эксплуатации от значений температуры при поверке в k-м поддиапазоне диапазона измерений, %',
        TRUE, 'MI3622'),
       ('theta_PDp', 'WinCC_OA.mi3622.' || 'theta_PDp',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием максимального отклонения давления измеряемой среды при эксплуатации от значений давления при поверке в k-м поддиапазоне диапазона измерений, %',
        TRUE, 'MI3622'),
       ('theta_Dt', 'WinCC_OA.mi3622.' || 'theta_Dt',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием максимального отклонения температуры измеряемой среды при эксплуатации от значений температуры при поверке в диапазоне измерений, %',
        TRUE, 'MI3622'),
       ('theta_Dp', 'WinCC_OA.mi3622.' || 'theta_Dp',
        'граница составляющей неисключенной систематической погрешности, обусловленной влиянием максимального отклонения давления измеряемой среды при эксплуатации от значений давления при поверке в диапазоне измерений, %',
        TRUE, 'MI3622'),
       ('MFOrK', 'WinCC_OA.mi3622.' || 'MFOrK',
        'способ реализации градуировочной характеристики СРМ: при определении коэффициента коррекции {MF} (по умолчанию) или при определении коэффициента преобразования {K}',
        TRUE, 'MI3622'),
       ('zeroStabilityCorr', 'WinCC_OA.mi3622.' || 'zeroStabilityCorr',
        'с коррекцией стабильности нуля {true} или без коррекции стабильности нуля {false} (по умолчанию)', TRUE,
        'MI3622'),
       ('rangeType', 'WinCC_OA.mi3622.' || 'rangeType',
        'вид реализации градуировочной характеристики СРМ: в рабочем диапазоне измерений {рабочий диапазон} (по умолчанию) или в поддиапазонах рабочего диапазона измерений {поддиапазон}',
        TRUE, 'MI3622'),
       ('operatingOrControlCPM', 'WinCC_OA.mi3622.' || 'operatingOrControlCPM',
        'СРМ применяется в качестве контрольного {контрольный} или рабочего {рабочий} (по умолчанию)', TRUE, 'MI3622'),
       ('Q_e_arr', 'WinCC_OA.mi3622.' || 'Q_e_arr', 'градуировочная характеристика ПР для расхода, т/ч', TRUE,
        'MI3622');

-- FinalData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('K_pm', 'WinCC_OA.mi3622.' || 'K_pm',
        'коэффициент преобразования поверяемого СРМ, соответствующий максимальному массовому расходу, имп/т', FALSE,
        'MI3622'),
       ('M_e_ij', 'WinCC_OA.mi3622.' || 'M_e_ij',
        'масса измеряемой среды, измеренная преобразователем массового расхода при i-ом измерении в j-й точке диапазона измерений расхода, т',
        FALSE, 'MI3622'),
       ('MF_ij', 'WinCC_OA.mi3622.' || 'MF_ij',
        'коэффициент коррекции поверяемого СРМ при i-ом измерении в j-й точке диапазона измерений расхода', FALSE,
        'MI3622'),
       ('MF', 'WinCC_OA.mi3622.' || 'MF',
        'среднее значение коэффициента коррекции поверяемого СРМ в диапазоне измерений', FALSE, 'MI3622'),
       ('MF_j', 'WinCC_OA.mi3622.' || 'MF_j',
        'среднее значение коэффициента коррекции поверяемого СРМ в j-й точке рабочего диапазона измерений расхода',
        FALSE, 'MI3622'),
       ('K', 'WinCC_OA.mi3622.' || 'K',
        'среднее значение коэффициента преобразования поверяемого СРМ в рабочем диапазоне измерений расхода, имп/т',
        FALSE, 'MI3622'),
       ('K_j', 'WinCC_OA.mi3622.' || 'K_j',
        'среднее значение коэффициента преобразования поверяемого СРМ в j-й точке рабочего диапазона измерений расхода, имп/т',
        FALSE, 'MI3622'),
       ('K_ij', 'WinCC_OA.mi3622.' || 'K_ij',
        'коэффициент преобразования поверяемого СРМ при i-ом измерении в j-й точке рабочего диапазона измерений расхода, имп/т',
        FALSE, 'MI3622'),
       ('MF_prime', 'WinCC_OA.mi3622.' || 'MF_prime', 'коэффициент коррекции поверяемого СРМ', FALSE, 'MI3622'),
       ('f_ij', 'WinCC_OA.mi3622.' || 'f_ij',
        'частота выходного сигнала поверяемого СРМ при i-ом измерении в j-й точке рабочего диапазона измерений расхода, Гц',
        FALSE, 'MI3622'),
       ('f_j', 'WinCC_OA.mi3622.' || 'f_j',
        'среднее значение частоты выходного сигнала поверяемого СРМ в j-й точке рабочего диапазона измерений расхода, Гц',
        FALSE, 'MI3622'),
       ('S_j', 'WinCC_OA.mi3622.' || 'S_j', 'СКО результатов измерений в j-й точке диапазона измерений расхода, %',
        FALSE, 'MI3622'),
       ('S_0j', 'WinCC_OA.mi3622.' || 'S_0j',
        'СКО среднего значения результатов измерений в каждой j-й точке рабочего диапазона измерений, %', FALSE,
        'MI3622'),
       ('eps_j', 'WinCC_OA.mi3622.' || 'eps_j',
        'граница случайной составляющей погрешности поверяемого СРМ в j-й точке рабочего диапазона измерений при доверительной вероятности Р=0,95, %',
        FALSE, 'MI3622'),
       ('t_095', 'WinCC_OA.mi3622.' || 't_095',
        'квантиль распределения Стьюдента при доверительной вероятности Р = 0,95', FALSE, 'MI3622'),
       ('eps_D', 'WinCC_OA.mi3622.' || 'eps_D',
        'граница случайной составляющей погрешности поверяемого СРМ в диапазоне измерений при доверительной вероятности Р=0,95, %',
        FALSE, 'MI3622'),
       ('Q_j', 'WinCC_OA.mi3622.' || 'Q_j',
        'значение расхода измеряемой среды в j-й точке рабочего диапазона измерений, т/ч', FALSE, 'MI3622'),
       ('theta_sigma_j', 'WinCC_OA.mi3622.' || 'theta_sigma_j',
        'граница неисключенной систематической погрешности в j-й точке диапазона измерений, %', FALSE, 'MI3622'),
       ('eps_PDk', 'WinCC_OA.mi3622.' || 'eps_PDk',
        'граница случайной составляющей погрешности поверяемого СРМ в k-ом поддиапазоне рабочего диапазона измерений при доверительной вероятности Р=0,95, %',
        FALSE, 'MI3622'),
       ('theta_sigma_D', 'WinCC_OA.mi3622.' || 'theta_sigma_D',
        'граница неисключенной систематической погрешности в диапазоне измерений, %', FALSE, 'MI3622'),
       ('theta_sigma_PDk', 'WinCC_OA.mi3622.' || 'theta_sigma_PDk',
        'граница неисключенной систематической погрешности в k-м поддиапазоне рабочего диапазона измерений, %', FALSE,
        'MI3622'),
       ('delta_j', 'WinCC_OA.mi3622.' || 'delta_j',
        'относительная погрешность СРМ (контрольного) в j-й точке диапазона измерений, %', FALSE, 'MI3622'),
       ('t_sigma_j', 'WinCC_OA.mi3622.' || 't_sigma_j',
        'коэффициент, зависящий от соотношения случайной и неисключенной систематической погрешностей в j-й точке рабочего диапазона измерений',
        FALSE, 'MI3622'),
       ('S_sigma_j', 'WinCC_OA.mi3622.' || 'S_sigma_j',
        'суммарное СКО результатов измерений в j-й точке рабочего диапазона измерений, %', FALSE, 'MI3622'),
       ('S_theta_j', 'WinCC_OA.mi3622.' || 'S_theta_j',
        'СКО суммы неисключенных систематических погрешностей в j-й точке рабочего диапазона измерений, %', FALSE,
        'MI3622'),
       ('delta_D', 'WinCC_OA.mi3622.' || 'delta_D',
        'относительная погрешность СРМ (рабочего) в рабочем диапазоне измерений, %', FALSE, 'MI3622'),
       ('delta_PDk', 'WinCC_OA.mi3622.' || 'delta_PDk',
        'относительная погрешность СРМ (рабочего) в k-м поддиапазоне рабочего диапазона измерений', FALSE, 'MI3622'),
       ('t_sigma_PDk', 'WinCC_OA.mi3622.' || 't_sigma_PDk',
        'коэффициент, зависящий от соотношения случайной и неисключенной систематической погрешностей в k-ом поддиапазоне рабочего диапазона измерений',
        FALSE, 'MI3622'),
       ('S_sigma_PDk', 'WinCC_OA.mi3622.' || 'S_sigma_PDk',
        'суммарное СКО результатов измерений в k-ом поддиапазоне рабочего диапазона измерений, %', FALSE, 'MI3622'),
       ('S_theta_PDk', 'WinCC_OA.mi3622.' || 'S_theta_PDk',
        'СКО суммы неисключенных систематических погрешностей в k-ом поддиапазоне рабочего диапазона измерений, %',
        FALSE, 'MI3622'),
       ('S_PDk', 'WinCC_OA.mi3622.' || 'S_PDk',
        'наибольшее значение из ряда СКО, в точках k-го поддиапазона рабочего диапазона измерений, %', FALSE,
        'MI3622'),
       ('theta_zj', 'WinCC_OA.mi3622.' || 'theta_zj',
        'граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ в j-й точке диапазона измерений, %',
        FALSE, 'MI3622'),
       ('theta_Dz', 'WinCC_OA.mi3622.' || 'theta_Dz',
        'граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ в диапазоне измерений, %',
        FALSE, 'MI3622'),
       ('Q_min', 'WinCC_OA.mi3622.' || 'Q_min', 'минимальное значение расхода в рабочем диапазоне измерений, т/ч',
        FALSE, 'MI3622'),
       ('Q_max', 'WinCC_OA.mi3622.' || 'Q_max', 'максимальное значение расхода в рабочем диапазоне измерений, т/ч',
        FALSE, 'MI3622'),
       ('Q_min_k', 'WinCC_OA.mi3622.' || 'Q_min_k',
        'минимальное значение расхода в k-ом поддиапазоне рабочего диапазона измерений, т/ч', FALSE, 'MI3622'),
       ('Q_max_k', 'WinCC_OA.mi3622.' || 'Q_max_k',
        'максимальное значение расхода в k-ом поддиапазоне рабочего диапазона измерений, т/ч', FALSE, 'MI3622'),
       ('theta_D', 'WinCC_OA.mi3622.' || 'theta_D',
        'граница составляющей неисключенной систематической погрешности, обусловленной погрешностью аппроксимации градуировочной характеристики для диапазона измерений, %',
        FALSE, 'MI3622'),
       ('theta_PDz', 'WinCC_OA.mi3622.' || 'theta_PDz',
        'граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ в k-м поддиапазоне диапазона измерений, %',
        FALSE, 'MI3622'),
       ('theta_PDk', 'WinCC_OA.mi3622.' || 'theta_PDk',
        'граница составляющей неисключенной систематической погрешности, обусловленной погрешностью аппроксимации градуировочной характеристики для k-го поддиапазона диапазона измерений, %',
        FALSE, 'MI3622'),
       ('conclusion', 'WinCC_OA.mi3622.' || 'conclusion', 'результат поверки (годен/не годен)', FALSE, 'MI3622');

-- InitialUnusedData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('delta_t_dop', 'WinCC_OA.mi3622.' || 'delta_t_dop', 'относительная погрешность, %', TRUE, 'MI3622'),
       ('delta_P_dop', 'WinCC_OA.mi3622.' || 'delta_P_dop', 'относительная погрешность, %', TRUE, 'MI3622'),
       ('t_min', 'WinCC_OA.mi3622.' || 't_min', 'минимальная температура, ºC', TRUE, 'MI3622'),
       ('t_max', 'WinCC_OA.mi3622.' || 't_max', 'максимальная температура, ºC', TRUE, 'MI3622'),
       ('P_min', 'WinCC_OA.mi3622.' || 'P_min', 'минимальное давление, МПа', TRUE, 'MI3622'),
       ('P_max', 'WinCC_OA.mi3622.' || 'P_max', 'максимальное давление, МПа', TRUE, 'MI3622'),
       ('t_ij', 'WinCC_OA.mi3622.' || 't_ij', 'температура, ºC', TRUE, 'MI3622'),
       ('P_ij', 'WinCC_OA.mi3622.' || 'P_ij', 'давление, МПа', TRUE, 'MI3622'),
       ('Q_p', 'WinCC_OA.mi3622.' || 'Q_p', 'градуировочная характеристика СРМ для расхода, т/ч', TRUE, 'MI3622'),
       ('f_p', 'WinCC_OA.mi3622.' || 'f_p', 'градуировочная характеристика СРМ для частоты, Гц', TRUE, 'MI3622'),
       ('K_y', 'WinCC_OA.mi3622.' || 'K_y', 'коэффициент градуировочной характеристики СРМ, имп/т (при MFp=1)', TRUE,
        'MI3622'),
       ('protocolNumber', 'WinCC_OA.mi3622.' || 'protocolNumber', 'номер протокола поверки', TRUE, 'MI3622');

-- InitialTextData
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('check_inspection', 'WinCC_OA.mi3622.' || 'check_inspection', 'внешний осмотр (осмотрели)', TRUE, 'MI3622'),
       ('check_leakproofness', 'WinCC_OA.mi3622.' || 'check_leakproofness',
        'проверка герметичности системы (герметично)', TRUE, 'MI3622'),
       ('check_software', 'WinCC_OA.mi3622.' || 'check_software',
        'подтверждение соответствия программного обеспечения (соответствует)', TRUE, 'MI3622'),
       ('CPM_name', 'WinCC_OA.mi3622.' || 'CPM_name', 'тип, модель, изготовитель СРМ', TRUE, 'MI3622'),
       ('inspector_full_name', 'WinCC_OA.mi3622.' || 'inspector_full_name', 'Ф.И.О проводившего поверку', TRUE,
        'MI3622'),
       ('poverka_place', 'WinCC_OA.mi3622.' || 'poverka_place', 'место проведения поверки', TRUE, 'MI3622'),
       ('inspector_position', 'WinCC_OA.mi3622.' || 'inspector_position', 'должность лица, проводившего поверку',
        TRUE, 'MI3622'),
       ('check_testing', 'WinCC_OA.mi3622.' || 'check_testing', 'опробование (опробовали)', TRUE, 'MI3622'),
       ('CPM_number', 'WinCC_OA.mi3622.' || 'CPM_number', 'заводской номер СРМ', TRUE, 'MI3622'),
       ('PR_name', 'WinCC_OA.mi3622.' || 'PR_name', 'тип, модель ПР', TRUE, 'MI3622'),
       ('poverka_method', 'WinCC_OA.mi3622.' || 'poverka_method', 'методика поверки', TRUE, 'MI3622'),
       ('CPM_owner', 'WinCC_OA.mi3622.' || 'CPM_owner', 'владелец СРМ', TRUE, 'MI3622'),
       ('PR_number', 'WinCC_OA.mi3622.' || 'PR_number', 'заводской номер ПР', TRUE, 'MI3622'),
       ('measureCount', 'WinCC_OA.mi3622.' || 'measureCount', 'кол-во измерений', TRUE, 'MI3622'),
       ('pointsCount', 'WinCC_OA.mi3622.' || 'pointsCount', 'кол-во точек', TRUE, 'MI3622'),
       ('S_theta_D', 'WinCC_OA.mi3622.' || 'S_theta_D',
        'граница составляющей неисключенной систематической погрешности, обусловленной погрешностью аппроксимации градуировочной характеристики для диапазона измерений, %',
        FALSE, 'MI3622'),
       ('S_sigma_D', 'WinCC_OA.mi3622.' || 'S_sigma_D',
        'суммарное СКО результатов измерений в рабочем диапазоне измерений, %', FALSE, 'MI3622'),
       ('t_sigma_D', 'WinCC_OA.mi3622.' || 't_sigma_D',
        'коэффициент, зависящий от соотношения случайной и неисключенной систематической погрешностей в рабочем диапазоне измерений',
        FALSE, 'MI3622'),
       ('S_D', 'WinCC_OA.mi3622.' || 'S_D', 'наибольшее значение из ряда СКО в точках диапазона измерений, %', FALSE,
        'MI3622');

-- Флаг, сигнализирующий о начале и конце поверки
INSERT INTO tag (permanent_name, address, description, initial, report_type)
VALUES ('isFinished', 'WinCC_OA.mi3622.' || 'isFinished', 'Флаг, сигнализирующий, что поверка завершена. При старте поверки в него записывается значение false, а после окончания - true', FALSE, 'MI3622');