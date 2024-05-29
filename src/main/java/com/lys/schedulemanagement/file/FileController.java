package com.lys.schedulemanagement.file;

import com.lys.schedulemanagement.file.model.FileDownloadRequestDto;
import com.lys.schedulemanagement.file.model.FileUploadRequestDto;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "File", description = "일정에 파일을 추가하는 API입니다.")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "선택한 일정에 첨부된 파일을 업로드하는 API입니다. jpg, png, jpeg 형식만 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "업로드된 파일이 지원되는 형식(jpg, jpeg, png)이 아닌 경우."),
            @ApiResponse(responseCode = "404", description = "지정된 `scheduleId`에 해당하는 일정이 존재하지 않는 경우")
    })
    public ResponseEntity<String> uploadFile(@ModelAttribute FileUploadRequestDto requestDto){
        try{
            fileService.uploadFile(requestDto);
            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다.");
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패했습니다.");
        }catch(ScheduleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/download")
    @Operation(summary = "선택한 일정 삭제", description = "선택한 일정에 첨부된 파일을 다운로드하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 다운로드 성공"),
            @ApiResponse(responseCode = "404", description = "scheduleId나 fileId에 해당하는 파일이 존재하지 않는 경우")
    })
    public ResponseEntity<Resource> downloadFile(@RequestBody FileDownloadRequestDto requestDto) throws IOException {
        Resource resource = fileService.downloadFile(requestDto);

        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
