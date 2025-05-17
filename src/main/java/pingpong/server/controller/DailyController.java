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
import pingpong.server.domain.Daily;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.request.DailyRequestDto;
import pingpong.server.service.DailyService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily")
public class DailyController {

    private final DailyService dailyService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody DailyRequestDto request) {
        int userId = userService.getLoginUser().getId();
        dailyService.create(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 생성 완료", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Daily>>> list() {
        int userId = userService.getLoginUser().getId();
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 목록 조회 성공", dailyService.getList(userId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable int id, @RequestBody DailyRequestDto request) {
        dailyService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 수정 완료", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable int id) {
        dailyService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 삭제 완료", null));
    }
}
