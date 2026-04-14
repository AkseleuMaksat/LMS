-- liquibase formatted sql

--changeset Akseleu:001-create-course-table
CREATE TABLE courses (
                         id               BIGSERIAL PRIMARY KEY,
                         name             VARCHAR(255) NOT NULL,
                         description      TEXT,
                         created_time     TIMESTAMP NOT NULL DEFAULT NOW(),
                         updated_time     TIMESTAMP NOT NULL DEFAULT NOW()
);

--changeset bitlab:001-create-chapter-table
CREATE TABLE chapters (
                          id               BIGSERIAL PRIMARY KEY,
                          name             VARCHAR(255) NOT NULL,
                          description      TEXT,
                          "order"          INT NOT NULL DEFAULT 0,
                          course_id        BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                          created_time     TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_time     TIMESTAMP NOT NULL DEFAULT NOW()
);

--changeset bitlab:001-create-lesson-table
CREATE TABLE lessons (
                         id               BIGSERIAL PRIMARY KEY,
                         name             VARCHAR(255) NOT NULL,
                         description      TEXT,
                         content          TEXT,
                         "order"          INT NOT NULL DEFAULT 0,
                         chapter_id       BIGINT NOT NULL REFERENCES chapters(id) ON DELETE CASCADE,
                         created_time     TIMESTAMP NOT NULL DEFAULT NOW(),
                         updated_time     TIMESTAMP NOT NULL DEFAULT NOW()
);