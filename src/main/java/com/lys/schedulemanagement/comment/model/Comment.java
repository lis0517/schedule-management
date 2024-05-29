package com.lys.schedulemanagement.comment.model;


import com.lys.schedulemanagement.schedule.model.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "author",nullable = false)
    private String author;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
}
