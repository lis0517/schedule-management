package com.lys.schedulemanagement.service;


import com.lys.schedulemanagement.dto.RequestDto;
import com.lys.schedulemanagement.dto.ResponseDto;
import com.lys.schedulemanagement.entity.Schedule;
import com.lys.schedulemanagement.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
