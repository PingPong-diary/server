package pingpong.server.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import pingpong.server.domain.UserDiary;
import pingpong.server.dto.ApiResponse;
import pingpong.server.service.UserDiaryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class UserDiaryController {

    private final UserDiaryService userDiaryService;

    @GetMapping("/{id}/members")
    public ResponseEntity<ApiResponse<List<UserDiary>>> getMembers(@PathVariable int id) {
        List<UserDiary> members = userDiaryService.getMembers(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "참여 멤버 조회 성공", members));
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ApiResponse<Void>> inviteMember(@PathVariable int id, @RequestParam int userId) {
        userDiaryService.inviteMember(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "초대 완료", null));
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(@PathVariable int id, @PathVariable int userId) {
        userDiaryService.removeMember(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "삭제 완료", null));
    }
}
