package com.lys.schedulemanagement.controller;


import com.lys.schedulemanagement.dto.RequestDto;
import com.lys.schedulemanagement.dto.ResponseDto;
import com.lys.schedulemanagement.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    public ResponseDto createSchedule(@RequestBody RequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/{id}")
    public ResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

}
