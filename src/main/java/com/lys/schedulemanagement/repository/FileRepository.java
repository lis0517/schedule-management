package com.lys.schedulemanagement.repository;

import com.lys.schedulemanagement.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
    Optional<File> findByIdAndScheduleId(Long fileId, Long scheduleId);
}