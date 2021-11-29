-- cp ~/projects/personal/PoEAA-kotlin/app/src/main/resources/table_definition/ddl.sql mysql:/tmp/

CREATE
DATABASE IF NOT EXISTS balance_sheet;

DROP TABLE IF EXISTS balance_sheet.sample;
CREATE TABLE balance_sheet.sample
(
    id char(36) NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sample';

DROP TABLE IF EXISTS balance_sheet.assignments;
DROP TABLE IF EXISTS balance_sheet.projects;
DROP TABLE IF EXISTS balance_sheet.engineers;

CREATE TABLE balance_sheet.engineers
(
    id         char(36)     NOT NULL,
    name       varchar(200) NOT NULL,
    unit_price BIGINT       NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='engineers';

CREATE TABLE balance_sheet.projects
(
    id   char(36)     NOT NULL,
    name varchar(200) NOT NULL,
    cost BIGINT       NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='projects';

CREATE TABLE balance_sheet.assignments
(
    engineer_id    char(36) NOT NULL,
    project_id     char(36) NOT NULL,
    commit_percent INT      NOT NULL,
    PRIMARY KEY (engineer_id, project_id),
    FOREIGN KEY fk_engineer_id (engineer_id) REFERENCES engineers(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fk_project_id(project_id) REFERENCES projects(id) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='assignments';
