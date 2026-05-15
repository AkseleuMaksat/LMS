package kz.bitlab.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_attachments")
@Setter
@Getter
public class Attachment extends BaseEntity {
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "url", nullable = false)
    String url;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    Lesson lesson;
}

