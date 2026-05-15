-- liquibase formatted sql

--changeset Akseleu:004-create-attachment-table
CREATE TABLE t_attachments (
                               id               BIGSERIAL PRIMARY KEY,
                               name             VARCHAR(255) NOT NULL,
                               url              VARCHAR(255) NOT NULL,
                               lesson_id        BIGINT REFERENCES lessons(id) ON DELETE CASCADE,
                               created_time     TIMESTAMP NOT NULL DEFAULT NOW(),
                               updated_time     TIMESTAMP NOT NULL DEFAULT NOW()
);
