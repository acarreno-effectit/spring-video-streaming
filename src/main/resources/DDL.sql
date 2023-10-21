CREATE DATABASE test_project;
---
CREATE SCHEMA IF NOT EXISTS streaming;
---
CREATE TABLE streaming.video(
    id_video UUID NOT NULL,
    filename VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    size_in_bytes INTEGER NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id_video)
);
---
CREATE TABLE streaming.metadata(
    id_metadata UUID NOT NULL,
    id_video UUID NOT NULL,
    title VARCHAR(256) NOT NULL,
    synopsis TEXT NULL,
    year_of_release INTEGER NULL,
    genre VARCHAR(128) NULL,
    running_time VARCHAR(50) NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id_metadata),
    CONSTRAINT fk_metadata FOREIGN KEY(id_video) REFERENCES streaming.video(id_video)
);
---
CREATE TABLE streaming.participant(
    id_participant UUID NOT NULL,
    id_video UUID NOT NULL,
    firstname VARCHAR(256) NULL,
    lastname VARCHAR(256) NULL,
    type VARCHAR(100) NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id_participant),
    CONSTRAINT fk_participant FOREIGN KEY(id_video) REFERENCES streaming.video(id_video)
);
---
CREATE TABLE streaming.action(
    id_action UUID NOT NULL,
    id_video UUID NOT NULL,
    username VARCHAR(128) NOT NULL,
    type VARCHAR(100) NULL,
    comment TEXT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id_action),
    CONSTRAINT fk_action FOREIGN KEY(id_video) REFERENCES streaming.video(id_video)
);