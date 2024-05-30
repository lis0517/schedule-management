package com.lys.schedulemanagement.comment;

import com.lys.schedulemanagement.comment.model.CommentRequestDto;
import com.lys.schedulemanagement.comment.model.CommentResponseDto;
import com.lys.schedulemanagement.comment.model.Comment;
import com.lys.schedulemanagement.schedule.model.Schedule;
import com.lys.schedulemanagement.comment.exception.CommentNotFoundException;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.user.exception.UnauthorizedExcpetion;
import com.lys.schedulemanagement.schedule.ScheduleRepository;
import jakarta.transaction.Transactional;
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
    public CommentResponseDto createComment(CommentRequestDto requestDto, String author) {

        Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
                ()-> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다. id: " + requestDto.getScheduleId()));

        Comment comment = requestDto.toEntity(schedule, author);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, String author) {

        Comment comment = checkAuthorAndGetComment(commentId, author);

        comment.setContent(requestDto.getContent());

        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long commentId, String author) {

        Comment comment = checkAuthorAndGetComment(commentId, author);

        commentRepository.delete(comment);
    }

    private Comment checkAuthorAndGetComment(Long commentId, String author){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다. id: " + commentId));

        if (!comment.getAuthor().equals(author)){
            throw new UnauthorizedExcpetion("작성자가 일치하지않습니다. author: " + author);
        }
        return comment;
    }

}
