package com.lys.schedulemanagement.controller;


import com.lys.schedulemanagement.dto.RequestDto;
import com.lys.schedulemanagement.dto.ResponseDto;
import com.lys.schedulemanagement.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "일정 관리 관련 API입니다.")
@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    @Operation(summary = "일정 작성", description = "제공받은 정보를 사용해서 새로운 일정을 만듭니다.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "일정이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "400", description = "유효하지않은 요청 매개변수입니다.")
    })
    public ResponseDto createSchedule(@Valid @RequestBody RequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "선택한 일정 조회", description = "선택한 일정의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 검색을 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없습니다.")
    })
    public ResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/list")
    @Operation(summary = "일정 목록 조회", description = "등록된 일정 전체를 조회합니다. 조회된 일정 목록은 작성일 기준 내림차순으로 정렬 되어있습니다.")
    @ApiResponse(responseCode = "200", description = "일정 목록 검색 성공")
    public List<ResponseDto> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }

    @PutMapping("/{id}")
    @Operation(summary = "선택한 일정 수정", description = "선택한 일정의 할일 제목, 할일 내용, 담당자를 수정할 수 있습니다. 수정 요청할 때 비밀번호를 함께 전달해야합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 업데이트를 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "유효하지않은 요청 매개변수입니다."),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없습니다.")
    })
    public ResponseDto updateSchedule(@PathVariable Long id, @Valid @RequestBody RequestDto requestDto){
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "선택한 일정 삭제", description = "선택한 일정을 삭제할 수 있습니다. 삭제 요청할 때 비밀번호를 함께 전달합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 삭제를 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없습니다.")
    })
    public void deleteSchedule(@PathVariable Long id, @RequestParam String password){
        scheduleService.deleteSchedule(id, password);
    }

}
