--liquibase formatted sql

--changeset alina.parfenteva:1
SET search_path TO manual_reports;

INSERT INTO report_type (id, name, description, active)
VALUES ('oilQualityPassport', 'Паспорт качества нефти', '', TRUE);

-- Входные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('export', 'oilQualPassp.' || 'export',
        'Нефть поставляется на экспорт или нет (true/false)',
        TRUE, 'oilQualityPassport'),
        ('passportNumber', 'oilQualPassp.' || 'passportNumber',
        'Номер паспорта качества нефти',
        TRUE, 'oilQualityPassport'),
       ('oilAcceptancePoint', 'oilQualPassp.' || 'oilAcceptancePoint',
        'Пункт приема-сдачи нефти',
        TRUE, 'oilQualityPassport'),
       ('labOrg', 'oilQualPassp.' || 'labOrg',
        'Лаборатория предприятия',
        TRUE, 'oilQualityPassport'),
       ('accreditationNumber', 'oilQualPassp.' || 'accreditationNumber',
        'Номер аттестата аккредитации',
        TRUE, 'oilQualityPassport'),
       ('SIKN', 'oilQualPassp.' || 'SIKN',
        'СИКН',
        TRUE, 'oilQualityPassport'),
       ('capacityMeasure', 'oilQualPassp.' || 'capacityMeasure',
        'Резервуар (мера вместимости)', TRUE, 'oilQualityPassport'),
       ('dateTimeSampling', 'oilQualPassp.' || 'dateTimeSampling',
        'Дата и время отбора пробы', TRUE, 'oilQualityPassport'),

--     Показатели
       ('tempMethod', 'oilQualPassp.' || 'tempMethod',
        'Температура нефти при условиях измерений объёма, ˚С. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('tempResult', 'oilQualPassp.' || 'tempResult',
        'Температура нефти при условиях измерений объёма, ˚С. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('pressureMethod', 'oilQualPassp.' || 'pressureMethod',
        'Давление нефти при условиях измерений объёма, МПа. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('pressureResult', 'oilQualPassp.' || 'pressureResult',
        'Давление нефти при условиях измерений объёма, МПа. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('initialDensityMethod', 'oilQualPassp.' || 'initialDensityMethod',
        'Плотность нефти при температуре и давлении в условиях измерений объема, кг/м3. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('initialDensityResult', 'oilQualPassp.' || 'initialDensityResult',
        'Плотность нефти при температуре и давлении в условиях измерений объема, кг/м3. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('density20Method', 'oilQualPassp.' || 'density20Method',
        'Плотность нефти при 20 ˚С, кг/м3. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('density20Result', 'oilQualPassp.' || 'density20Result',
        'Плотность нефти при 20 ˚С, кг/м3. Результат испытаний',
        TRUE, 'oilQualityPassport'),

       ('density15Method', 'oilQualPassp.' || 'density15Method',
        'Плотность нефти при 15 ˚С, кг/м3. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('density15Result', 'oilQualPassp.' || 'density15Result',
        'Плотность нефти при 15 ˚С, кг/м3. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('waterFractionMethod', 'oilQualPassp.' || 'waterFractionMethod',
        'Массовая доля воды, %. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('waterFractionResult', 'oilQualPassp.' || 'waterFractionResult',
        'Массовая доля воды, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('chlorideSaltFractionMethod', 'oilQualPassp.' || 'chlorideSaltFractionMethod',
        'Массовая концентрация хлористых солей, мг/дм3 (%). Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('chlorideSaltFractionResult', 'oilQualPassp.' || 'chlorideSaltFractionResult',
        'Массовая концентрация хлористых солей, мг/дм3 (%). Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('admixtureFractionMethod', 'oilQualPassp.' || 'admixtureFractionMethod',
        'Массовая доля механических примесей, %. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('admixtureFractionResult', 'oilQualPassp.' || 'admixtureFractionResult',
        'Массовая доля механических примесей, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('sulfurFractionMethod', 'oilQualPassp.' || 'sulfurFractionMethod',
        'Массовая доля серы, %. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('sulfurFractionResult', 'oilQualPassp.' || 'sulfurFractionResult',
        'Массовая доля серы, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('steamPressureMethod', 'oilQualPassp.' || 'steamPressureMethod',
        'Давление насыщенных паров, кПа (мм рт. ст.). Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('steamPressureResult', 'oilQualPassp.' || 'steamPressureResult',
        'Давление насыщенных паров, кПа (мм рт. ст.). Результат испытаний',
        TRUE, 'oilQualityPassport'),

       ('fractionOutMethod', 'oilQualPassp.' || 'fractionOutMethod',
        'Выход фракций, %. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('fractionOut200Result', 'oilQualPassp.' || 'fractionOut200Result',
        'Выход фракций при температуре до 200 ˚С, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('fractionOut300Result', 'oilQualPassp.' || 'fractionOut300Result',
        'Выход фракций при температуре до 300 ˚С, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),

       ('paraffinFractionMethod', 'oilQualPassp.' || 'paraffinFractionMethod',
        'Массовая доля парафина, %. Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('paraffinFractionResult', 'oilQualPassp.' || 'paraffinFractionResult',
        'Массовая доля парафина, %. Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('hydroSulfideFractionMethod', 'oilQualPassp.' || 'hydroSulfideFractionMethod',
        'Массовая доля сероводорода, млн-1(ppm). Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('hydroSulfideFractionResult', 'oilQualPassp.' || 'hydroSulfideFractionResult',
        'Массовая доля сероводорода, млн-1(ppm). Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('methFractionMethod', 'oilQualPassp.' || 'methFractionMethod',
        'Массовая доля метил-и этилмеркаптанов в сумме, млн-1(ppm). Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('methFractionResult', 'oilQualPassp.' || 'methFractionResult',
        'Массовая доля метил-и этилмеркаптанов в сумме, млн-1(ppm). Результат испытаний',
        TRUE, 'oilQualityPassport'),
       ('organicFractionMethod', 'oilQualPassp.' || 'organicFractionMethod',
        'Массовая доля органических хлоридов во фракции, выкипающей до температуры 204˚С , млн-1(ppm). Метод испытаний',
        TRUE, 'oilQualityPassport'),
       ('organicFractionResult', 'oilQualPassp.' || 'organicFractionResult',
        'Массовая доля органических хлоридов во фракции, выкипающей до температуры 204˚С , млн-1(ppm). Результат испытаний',
        TRUE, 'oilQualityPassport'),

       -- Нижняя часть
       ('labName', 'oilQualPassp.' || 'labName',
        'И.О.Фамилия представителя испытательной лаборатории',
        TRUE, 'oilQualityPassport'),
       ('deliverPosition', 'oilQualPassp.' || 'deliverPosition',
        'Должность представителя сдающей стороны',
        TRUE, 'oilQualityPassport'),
       ('deliverOrg', 'oilQualPassp.' || 'deliverOrg',
        'Название предприятия сдающей стороны',
        TRUE, 'oilQualityPassport'),
       ('deliverName', 'oilQualPassp.' || 'deliverName',
        'И.О.Фамилия представителя сдающей стороны',
        TRUE, 'oilQualityPassport'),
       ('receiverPosition', 'oilQualPassp.' || 'receiverPosition',
        'Должность представителя принимающей стороны',
        TRUE, 'oilQualityPassport'),
       ('receiverOrg', 'oilQualPassp.' || 'receiverOrg',
        'Название предприятия принимающей стороны',
        TRUE, 'oilQualityPassport'),
       ('receiverName', 'oilQualPassp.' || 'receiverName',
        'И.О.Фамилия представителя принимающей стороны',
        TRUE, 'oilQualityPassport');

-- выходные данные
INSERT INTO tag (permanent_name, address, description, initial, report_type_id)
VALUES ('GOST', 'oilQualPassp.' || 'GOST',
        'Обозначение нефти по ГОСТ Р 51858',
        FALSE, 'oilQualityPassport');

--rollback delete from tag where report_type_id = 'oilQualityPassport'
--rollback delete from report_type where id = 'oilQualityPassport'