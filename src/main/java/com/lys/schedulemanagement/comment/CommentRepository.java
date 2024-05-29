package com.lys.schedulemanagement.comment;

import com.lys.schedulemanagement.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleIdOrderByCreatedAtDesc(Long scheduleId);
}
