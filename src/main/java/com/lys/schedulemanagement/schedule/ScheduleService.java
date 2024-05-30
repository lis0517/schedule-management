package com.lys.schedulemanagement.schedule;


import com.lys.schedulemanagement.schedule.model.PasswordDto;
import com.lys.schedulemanagement.schedule.model.RequestDto;
import com.lys.schedulemanagement.schedule.model.ResponseDto;
import com.lys.schedulemanagement.schedule.model.Schedule;
import com.lys.schedulemanagement.schedule.exception.PasswordMismatchException;
import com.lys.schedulemanagement.schedule.exception.ScheduleNotFoundException;
import com.lys.schedulemanagement.file.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        Schedule schedule = checkPWAndGetSchedule(id, requestDto.getPassword());

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        schedule.setAuthor(requestDto.getAuthor());

        scheduleRepository.save(schedule);
        return new ResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, PasswordDto passwordDto) {
        Schedule schedule = checkPWAndGetSchedule(id, passwordDto.getPassword());

        // 일정과 연관된 파일 삭제
        fileRepository.deleteByScheduleId(id);

        scheduleRepository.delete(schedule);
    }

    private Schedule checkPWAndGetSchedule(Long id, String password){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지않습니다. id = " + id));
        if(!schedule.getPassword().equals(password)){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        return schedule;
    }
}
