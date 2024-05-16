package com.lys.schedulemanagement.controller;

import com.lys.schedulemanagement.dto.FileDownloadRequestDto;
import com.lys.schedulemanagement.dto.FileUploadRequestDto;
import com.lys.schedulemanagement.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.service.FileService;
import com.lys.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
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
    public ResponseEntity<Resource> downloadFile(@RequestBody FileDownloadRequestDto requestDto) throws IOException {
        Resource resource = fileService.downloadFile(requestDto);

        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
