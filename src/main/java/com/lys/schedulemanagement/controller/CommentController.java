package com.lys.schedulemanagement.controller;


import com.lys.schedulemanagement.dto.CommentRequestDto;
import com.lys.schedulemanagement.dto.CommentResponseDto;
import com.lys.schedulemanagement.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment", description = "댓글을 추가하는 API입니다.")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/create")
    @Operation(summary = "댓글 달기", description = "선택한 일정에 댓글을 추가하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 업로드 성공"),
            @ApiResponse(responseCode = "404", description = "지정된 `scheduleId`에 해당하는 일정이 존재하지 않는 경우")
    })
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
