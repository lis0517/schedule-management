package com.lys.schedulemanagement.comment;


import com.lys.schedulemanagement.comment.model.CommentRequestDto;
import com.lys.schedulemanagement.comment.model.CommentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String author = userDetails.getUsername();
        CommentResponseDto responseDto = commentService.createComment(requestDto, author);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "선택한 일정에 있는 댓글을 수정하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "401", description = "댓글 작성자와 일치하지않습니다."),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지않습니다.")
    })
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails){
        String author = userDetails.getUsername();
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, author);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "선택한 일정에 있는 댓글을 삭제하는 API입니다.")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails){
        String author = userDetails.getUsername();
        commentService.deleteComment(commentId, author);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
