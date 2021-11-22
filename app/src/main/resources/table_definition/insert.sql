TRUNCATE balance_sheet.sample;
INSERT INTO balance_sheet.sample
VALUES ('04d88a00-e4b0-4f6e-95ee-1d30d84e2a27');

DELETE FROM balance_sheet.assignments;
DELETE FROM balance_sheet.projects;
DELETE FROM balance_sheet.engineers;

INSERT INTO balance_sheet.engineers
VALUES ('d6f2ace2-ba0d-4b44-b879-36c2accca949', 'keketon', 1000000),
       ('e822c10f-7300-4379-3877-b8a2007c6a0e', 'yuyuton', 2000000);

INSERT INTO balance_sheet.projects
VALUES ('80811c8f-9332-4ab5-9e82-d563e3a20eb2', 'projectX', 0),
       ('aed1a658-4ef4-7faa-651e-0ce613601312', 'projectY', 0);

INSERT INTO balance_sheet.assignments
VALUES ('d6f2ace2-ba0d-4b44-b879-36c2accca949', '80811c8f-9332-4ab5-9e82-d563e3a20eb2', 100),
       ('e822c10f-7300-4379-3877-b8a2007c6a0e', '80811c8f-9332-4ab5-9e82-d563e3a20eb2', 20),
       ('e822c10f-7300-4379-3877-b8a2007c6a0e', 'aed1a658-4ef4-7faa-651e-0ce613601312', 80);