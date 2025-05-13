package pingpong.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.Diary;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.request.DiaryRequestDto;
import pingpong.server.service.DiaryService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody DiaryRequestDto request) {
        int userId = userService.getLoginUser().getId();
        diaryService.createDiary(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 생성 완료", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Diary>>> list() {
        int userId = userService.getLoginUser().getId();
        return ResponseEntity.ok(new ApiResponse<>(true, "다이어리 목록 조회", diaryService.getDiaryList(userId)));
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
}
