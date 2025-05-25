package pingpong.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.CalendarDiaryDto;
import pingpong.server.dto.request.DiaryRequestDto;
import pingpong.server.dto.response.DiaryResponseDto;
import pingpong.server.service.AuthService;
import pingpong.server.service.DiaryService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody DiaryRequestDto request) {
        int userId = authService.getLoginUser().getId();
        diaryService.createDiary(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 생성 완료", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<DiaryResponseDto>>> list() {
        int userId = authService.getLoginUser().getId();
        List<DiaryResponseDto> list = diaryService.getDiarySharedList(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 목록 조회", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable int id, @RequestBody DiaryRequestDto request) {
        diaryService.updateDiary(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 수정 완료", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable int id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 삭제 완료", null));
    }
    
    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<List<CalendarDiaryDto>>> getCalendarEmotions() {
        int userId = authService.getLoginUser().getId();
        List<CalendarDiaryDto> result = diaryService.getCalendarEmotionList(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "달력 감정 리스트 조회 완료", result));
    }

}
