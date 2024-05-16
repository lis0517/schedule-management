package com.lys.schedulemanagement.service;


import com.lys.schedulemanagement.dto.PasswordDto;
import com.lys.schedulemanagement.dto.RequestDto;
import com.lys.schedulemanagement.dto.ResponseDto;
import com.lys.schedulemanagement.entity.Schedule;
import com.lys.schedulemanagement.exception.PasswordMismatchException;
import com.lys.schedulemanagement.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.repository.FileRepository;
import com.lys.schedulemanagement.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final FileRepository fileRepository;

    @Transactional
    public ResponseDto createSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        schedule.setAuthor(requestDto.getAuthor());
        schedule.setPassword(requestDto.getPassword());
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleRepository.save(schedule);
        return new ResponseDto(schedule);
    }

    @Transactional
    public ResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지않습니다. id = " + id));
        return new ResponseDto(schedule);
    }

    @Transactional
    public List<ResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new).toList();
    }

    @Transactional
    public ResponseDto updateSchedule(Long id, RequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지않습니다. id = " + id));
        if(!schedule.getPassword().equals(requestDto.getPassword())){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        schedule.setAuthor(requestDto.getAuthor());
        scheduleRepository.save(schedule);
        return new ResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, PasswordDto passwordDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지않습니다. id = " + id));
        if(!schedule.getPassword().equals(passwordDto.getPassword())){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // 일정과 연관된 파일 삭제
        fileRepository.deleteByScheduleId(id);

        scheduleRepository.delete(schedule);
    }
}
