package pingpong.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.Friend;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.request.FriendRequestDto;
import pingpong.server.service.FriendService;
import pingpong.server.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<Void>> sendRequest(@RequestBody FriendRequestDto request) {
        int userId = userService.getLoginUser().getId();
        friendService.sendRequest(userId, request.getFriendId());
        return ResponseEntity.ok(new ApiResponse<>(true, "친구 요청 완료", null));
    }

    @PostMapping("/requests/{id}/accept")
    public ResponseEntity<ApiResponse<Void>> acceptRequest(@PathVariable("id") int friendId) {
        int userId = userService.getLoginUser().getId();
        friendService.acceptRequest(userId, friendId);
        return ResponseEntity.ok(new ApiResponse<>(true, "친구 요청 수락", null));
    }

    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectRequest(@PathVariable("id") int friendId) {
        int userId = userService.getLoginUser().getId();
        friendService.rejectRequest(userId, friendId);
        return ResponseEntity.ok(new ApiResponse<>(true, "친구 요청 거절", null));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<ApiResponse<Void>> deleteFriend(@PathVariable int friendId) {
        int userId = userService.getLoginUser().getId();
        friendService.deleteFriend(userId, friendId);
        return ResponseEntity.ok(new ApiResponse<>(true, "친구 삭제 완료", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Friend>>> getFriendList() {
        int userId = userService.getLoginUser().getId();
        List<Friend> friends = friendService.getFriendList(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "친구 목록 조회 성공", friends));
    }

    @GetMapping("/requests/incoming")
    public ResponseEntity<ApiResponse<List<Friend>>> getIncomingRequests() {
        int userId = userService.getLoginUser().getId();
        List<Friend> requests = friendService.getIncomingRequests(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "받은 친구 요청 목록", requests));
    }

    @GetMapping("/requests/outgoing")
    public ResponseEntity<ApiResponse<List<Friend>>> getOutgoingRequests() {
        int userId = userService.getLoginUser().getId();
        List<Friend> requests = friendService.getOutgoingRequests(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "보낸 친구 요청 목록", requests));
    }
}
