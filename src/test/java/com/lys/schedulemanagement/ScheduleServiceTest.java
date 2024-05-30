package com.lys.schedulemanagement;

import com.lys.schedulemanagement.schedule.model.PasswordDto;
import com.lys.schedulemanagement.schedule.model.RequestDto;
import com.lys.schedulemanagement.schedule.model.ResponseDto;
import com.lys.schedulemanagement.schedule.model.Schedule;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.schedule.ScheduleRepository;
import com.lys.schedulemanagement.schedule.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private Schedule schedule;
    private RequestDto requestDto;

    private PasswordDto passwordDto;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setTitle("Test Schedule");
        schedule.setContent("Test Content");
        schedule.setAuthor("test@example.com");
        schedule.setPassword("1234");

        requestDto = new RequestDto();
        requestDto.setTitle("Test Schedule");
        requestDto.setContent("Test Content");
        requestDto.setAuthor("test@example.com");
        requestDto.setPassword("1234");

        passwordDto = new PasswordDto("1234");
    }

    @Test
    void createSchedule_ValidRequest_Success() {
        // given
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // when
        ResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        //assertNotNull(responseDto.getId());
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthor(), responseDto.getAuthor());
    }

    @Test
    void getSchedule_ValidId_Success() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        // when
        ResponseDto responseDto = scheduleService.getSchedule(1L);

        // then
        assertEquals(schedule.getId(), responseDto.getId());
        assertEquals(schedule.getTitle(), responseDto.getTitle());
        assertEquals(schedule.getContent(), responseDto.getContent());
        assertEquals(schedule.getAuthor(), responseDto.getAuthor());
    }

    @Test
    void getSchedule_InvalidId_ThrowsException() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.getSchedule(1L));
    }

    @Test
    void getAllSchedules_Success() {
        // given
        when(scheduleRepository.findAllByOrderByCreatedAtDesc()).thenReturn(Collections.singletonList(schedule));

        // when
        List<ResponseDto> responseDtos = scheduleService.getAllSchedules();

        // then
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        assertEquals(schedule.getId(), responseDtos.get(0).getId());
    }

    @Test
    void updateSchedule_ValidRequest_Success() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // when
        ResponseDto responseDto = scheduleService.updateSchedule(1L, requestDto);

        // then
        assertEquals(schedule.getId(), responseDto.getId());
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthor(), responseDto.getAuthor());
    }

    @Test
    void deleteSchedule_ValidId_Success() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        // when
        scheduleService.deleteSchedule(1L, passwordDto);

        // then
        verify(scheduleRepository).delete(schedule);
    }

    @Test
    void deleteSchedule_InvalidId_ThrowsException() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.deleteSchedule(1L, passwordDto));
    }
}
