package com.lys.schedulemanagement.comment.model;

import com.lys.schedulemanagement.schedule.model.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
    @NotBlank(message = "일정 아이디를 입력해주세요.")
    private Long scheduleId;

    public Comment toEntity(Schedule schedule, String author){
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setSchedule(schedule);
        return comment;
    }
}
