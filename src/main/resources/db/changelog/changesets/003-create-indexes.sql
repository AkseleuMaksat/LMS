-- liquibase formatted sql

--changeset Akseleu:003-create-fk-indexes
CREATE INDEX idx_chapters_course_id ON chapters(course_id);
CREATE INDEX idx_lessons_chapter_id ON lessons(chapter_id);
