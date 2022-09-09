CREATE TABLE IF NOT EXISTS settings
(
    id    SERIAL PRIMARY KEY,
    "group" VARCHAR(32),
    name  VARCHAR(256),
    value VARCHAR(256)
);

INSERT INTO settings ("group", name,value)
VALUES ('report view', 'hour report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'daily report columns', 'sikn,il1,il2,bik'),
       ('report view', 'shift report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'month report columns', 'sikn,il1,il2,il3,il4,bik'),
       ('report view', 'year report columns', 'sikn,il1,il2,il3,il4,bik');