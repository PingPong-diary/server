package pingpong.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.UserDaily;
import pingpong.server.dto.ApiResponse;
import pingpong.server.service.AuthService;
import pingpong.server.service.UserDailyService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily/{dailyId}/members")
public class UserDailyController {

    private final UserDailyService userDailyService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDaily>>> getMembers(@PathVariable int dailyId) {
        List<UserDaily> members = userDailyService.getMembers(dailyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 참여 멤버 조회 성공", members));
    }

    @PostMapping("/share")
    public ResponseEntity<ApiResponse<Void>> inviteMember(@PathVariable int dailyId, @RequestParam int userId) {
        int requesterId = authService.getLoginUser().getId();
        userDailyService.inviteMember(requesterId, dailyId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 공유 완료", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(@PathVariable int dailyId, @PathVariable int userId) {
        int requesterId = authService.getLoginUser().getId();
        userDailyService.removeMember(requesterId, dailyId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "일정 멤버 삭제 완료", null));
    }
}
