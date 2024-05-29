package com.lys.schedulemanagement.file;

import com.lys.schedulemanagement.file.model.FileDownloadRequestDto;
import com.lys.schedulemanagement.file.model.FileUploadRequestDto;
import com.lys.schedulemanagement.file.model.File;
import com.lys.schedulemanagement.schedule.model.Schedule;
import com.lys.schedulemanagement.file.exception.DownloadFileNotFoundException;
import com.lys.schedulemanagement.file.exception.FileTypeMismatchException;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.file.FileRepository;
import com.lys.schedulemanagement.schedule.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void uploadFile(FileUploadRequestDto requestDto) throws IOException{
        Long id = requestDto.getScheduleId();
        MultipartFile file = requestDto.getFile();

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        String fileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(fileName);
        if (!isAllowedExtension(fileExtension)){
            throw new FileTypeMismatchException("허용되지 않은 파일 형식입니다. 파일 이름: " + fileName);
        }

        System.out.println(fileName);
        String filePath = "uploads/" + fileName;
        System.out.println(filePath);

        java.io.File directory = new java.io.File("uploads");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        java.io.File destinationFile = new java.io.File(filePath);
        try(InputStream inputStream = file.getInputStream(); // 권한 문제인지 file.transferTo(destinationFile)에서 계속 IOException 발생
            OutputStream outputStream= new FileOutputStream(destinationFile)){
            byte[] buffer = new byte[8192];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        File uploadedFile = new File(null, fileName, filePath, schedule);
        fileRepository.save(uploadedFile);
    }


    @Transactional
    public Resource downloadFile(FileDownloadRequestDto requestDto) throws IOException{
        Long fileId = requestDto.getFileId();
        Long scheduleId = requestDto.getScheduleId();

        // 파일 정보 조회
        File file = fileRepository.findByIdAndScheduleId(fileId, scheduleId)
                .orElseThrow(()-> new DownloadFileNotFoundException("파일을 찾을 수 없습니다. fileId: " + fileId + ", scheduleId: " + scheduleId)) ;

        // 파일 다운로드
        String filePath = file.getFilePath();
        Resource resource = new UrlResource("file:" + filePath);

        if (resource.exists()){
            return resource;
        }else{
            throw new DownloadFileNotFoundException("파일을 찾을 수 없습니다. filePath: " + filePath);
        }
    }

    // 파일 확장자 추출
    private String getFileExtension(String fileName){
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1){
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    // 파일 확장자 검사
    private boolean isAllowedExtension(String extension){
        List<String> allowedExtensions = Arrays.asList("jpg","jpeg","png");
        return allowedExtensions.contains(extension);
    }
}
