package com.lys.schedulemanagement.file.model;

import com.lys.schedulemanagement.schedule.model.Schedule;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name="schedule_id", nullable = false)
    private Schedule schedule;
}
