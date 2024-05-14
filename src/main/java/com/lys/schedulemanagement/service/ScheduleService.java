package com.lys.schedulemanagement.service;


import com.lys.schedulemanagement.dto.RequestDto;
import com.lys.schedulemanagement.dto.ResponseDto;
import com.lys.schedulemanagement.entity.Schedule;
import com.lys.schedulemanagement.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

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
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지않습니다. id = " + id));
        return new ResponseDto(schedule);
    }

    @Transactional
    public List<ResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new).toList();
    }

    @Transactional
    public ResponseDto updateSchedule(Long id, RequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지않습니다. id = " + id));
        if(!schedule.getPassword().equals(requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        schedule.setAuthor(requestDto.getAuthor());
        scheduleRepository.save(schedule);
        return new ResponseDto(schedule);
    }
}
