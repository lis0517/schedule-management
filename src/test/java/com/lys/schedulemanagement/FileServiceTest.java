package com.lys.schedulemanagement;

import com.lys.schedulemanagement.file.model.FileDownloadRequestDto;
import com.lys.schedulemanagement.file.model.FileUploadRequestDto;
import com.lys.schedulemanagement.file.model.File;
import com.lys.schedulemanagement.schedule.model.Schedule;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.file.FileRepository;
import com.lys.schedulemanagement.schedule.ScheduleRepository;
import com.lys.schedulemanagement.file.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private FileService fileService;

    private Schedule schedule;
    private File file;

    @BeforeEach
    void setup(){
        schedule = new Schedule();
        schedule.setId(1L);

        file = new File();
        file.setId(1L);
        file.setFileName("test.jpg");
        file.setFilePath("uploads/test.jpg");
        file.setSchedule(schedule);
    }

    @Test
    void uploadFile_ValidFile_Success() throws IOException{
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpg",
                "test".getBytes());

        FileUploadRequestDto requestDto = new FileUploadRequestDto();
        requestDto.setScheduleId(1L);
        requestDto.setFile(multipartFile);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        //when
        fileService.uploadFile(requestDto);

        //then
        assertNotNull(file.getId());
        assertEquals("test.jpg", file.getFileName());
        assertEquals("uploads/test.jpg", file.getFilePath());
        assertEquals(schedule, file.getSchedule());
    }

    @Test
    void uploadFile_InvalidScheduleId_ThrowsException() {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test".getBytes());

        FileUploadRequestDto requestDto = new FileUploadRequestDto();
        requestDto.setScheduleId(1L);
        requestDto.setFile(multipartFile);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ScheduleNotFoundException.class, () -> fileService.uploadFile(requestDto));
    }

    @Test
    void downloadFile_ValidFileId_Success() throws IOException {
        // given
        FileDownloadRequestDto requestDto = new FileDownloadRequestDto();
        requestDto.setScheduleId(1L);
        requestDto.setFileId(1L);

        when(fileRepository.findByIdAndScheduleId(1L, 1L)).thenReturn(Optional.of(file));

        // when
        Resource resource = fileService.downloadFile(requestDto);

        // then
        assertNotNull(resource);
        assertEquals("test.jpg", resource.getFilename());
    }

    @Test
    void downloadFile_InvalidFileId_ThrowsException() {
        // given
        FileDownloadRequestDto requestDto = new FileDownloadRequestDto();
        requestDto.setScheduleId(1L);
        requestDto.setFileId(1L);

        when(fileRepository.findByIdAndScheduleId(1L, 1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RuntimeException.class, () -> fileService.downloadFile(requestDto));
    }
}
