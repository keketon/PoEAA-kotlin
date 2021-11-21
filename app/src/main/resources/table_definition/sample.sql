CREATE DATABASE IF NOT EXISTS balance_sheet;

DROP TABLE IF EXISTS balance_sheet.sample;
CREATE TABLE balance_sheet.sample(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sample';

INSERT INTO balance_sheet.sample
VALUES (1);

DROP TABLE IF EXISTS balance_sheet.engineers;
CREATE TABLE balance_sheet.engineers(
    id BIGINT NOT NULL,
    name varchar(200) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='engineers';

INSERT INTO balance_sheet.engineers
VALUES (1, 'keketon');

DROP TABLE IF EXISTS balance_sheet.projects;
CREATE TABLE balance_sheet.projects(
    id BIGINT NOT NULL,
    name varchar(200) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='projects';

INSERT INTO balance_sheet.projects
VALUES (1, 'projectX');

DROP TABLE IF EXISTS balance_sheet.assignments;
CREATE TABLE balance_sheet.assignments(
    engineer_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    commit_percent INT NOT NULL,
    PRIMARY KEY (engineer_id, project_id),
    FOREIGN KEY fk_engineer_id (engineer_id) REFERENCES engineers(id),
    FOREIGN KEY fk_project_id(project_id) REFERENCES projects(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='assignments';

INSERT INTO balance_sheet.assignments
VALUES (1, 1, 100);
