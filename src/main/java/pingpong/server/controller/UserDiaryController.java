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
import pingpong.server.domain.UserDiary;
import pingpong.server.dto.ApiResponse;
import pingpong.server.service.AuthService;
import pingpong.server.service.UserDiaryService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary/{diaryId}/members")  
public class UserDiaryController {

    private final UserDiaryService userDiaryService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDiary>>> getMembers(@PathVariable int diaryId) {
        List<UserDiary> members = userDiaryService.getMembers(diaryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "참여 멤버 조회 성공", members));
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<Void>> inviteMember(
            @PathVariable int diaryId,
            @RequestParam int userId) {
        int requesterId = authService.getLoginUser().getId();
        userDiaryService.inviteMember(requesterId, diaryId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "초대 완료", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(
            @PathVariable int diaryId,
            @PathVariable int userId) {
        int requesterId = authService.getLoginUser().getId();
        userDiaryService.removeMember(requesterId, diaryId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "삭제 완료", null));
    }
}
