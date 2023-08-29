--liquibase formatted sql

--changeset alina.parfenteva:1
CREATE INDEX
    ON manual_reports.report_data (report_id);

--changeset alina.parfenteva:2
CREATE INDEX
    ON scheduled_reports.report_data (report_id);