CREATE DATABASE IF NOT EXISTS balance_sheet;

DROP TABLE IF EXISTS balance_sheet.sample;
CREATE TABLE balance_sheet.sample(
    id char(36) NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sample';

INSERT INTO balance_sheet.sample
VALUES ('04d88a00-e4b0-4f6e-95ee-1d30d84e2a27');

DROP TABLE IF EXISTS balance_sheet.engineers;
CREATE TABLE balance_sheet.engineers(
    id char(36) NOT NULL,
    name varchar(200) NOT NULL,
    unit_price BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='engineers';

INSERT INTO balance_sheet.engineers
VALUES ('d6f2ace2-ba0d-4b44-b879-36c2accca949', 'keketon', 1000000);

DROP TABLE IF EXISTS balance_sheet.projects;
CREATE TABLE balance_sheet.projects(
    id char(36) NOT NULL,
    name varchar(200) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='projects';

INSERT INTO balance_sheet.projects
VALUES ('80811c8f-9332-4ab5-9e82-d563e3a20eb2', 'projectX');

DROP TABLE IF EXISTS balance_sheet.assignments;
CREATE TABLE balance_sheet.assignments(
    engineer_id char(36) NOT NULL,
    project_id char(36) NOT NULL,
    commit_percent INT NOT NULL,
    PRIMARY KEY (engineer_id, project_id),
    FOREIGN KEY fk_engineer_id (engineer_id) REFERENCES engineers(id),
    FOREIGN KEY fk_project_id(project_id) REFERENCES projects(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='assignments';

INSERT INTO balance_sheet.assignments
VALUES ('d6f2ace2-ba0d-4b44-b879-36c2accca949', '80811c8f-9332-4ab5-9e82-d563e3a20eb2', 100);
