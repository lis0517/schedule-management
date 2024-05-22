package com.lys.schedulemanagement.service;

import com.lys.schedulemanagement.dto.CommentRequestDto;
import com.lys.schedulemanagement.dto.CommentResponseDto;
import com.lys.schedulemanagement.entity.Comment;
import com.lys.schedulemanagement.entity.Schedule;
import com.lys.schedulemanagement.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.repository.CommentRepository;
import com.lys.schedulemanagement.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository){
        super();
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {

        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                ()-> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다. id: " + requestDto.getScheduleId()));

        Comment comment = requestDto.toEntity(schedule);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }
}
