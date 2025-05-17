package pingpong.server.controller;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pingpong.server.domain.User;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.request.MailRequestDto;
import pingpong.server.dto.request.ResetPwRequestDto;
import pingpong.server.dto.response.ChangePwRequestDto;
import pingpong.server.dto.response.UserSearchResponseDto;
import pingpong.server.service.MailService;
import pingpong.server.service.MailVerificationService;
import pingpong.server.service.UserService;
import pingpong.server.util.JwtUtil;

@RestController	
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final MailVerificationService mailVerificationService;

    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@RequestParam String email) {
        boolean isDuplicate = userService.isEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(true, "이메일 중복 확인 완료", isDuplicate));
    }

    @GetMapping("/token")
    public ResponseEntity<ApiResponse<User>> getLoginUser() {
        User user = userService.getLoginUser();
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "유저 조회 성공", user));
        } else {
            return ResponseEntity.status(401)
                .body(new ApiResponse<>(false, "인증되지 않은 사용자입니다.", null));
        }
    }

    @PatchMapping("/change-pw")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePwRequestDto request) {
        try {
            userService.changePassword(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "비밀번호가 변경되었습니다.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/pw/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPwRequestDto request) {
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "비밀번호가 재설정되었습니다.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/email/request-code")
    public ResponseEntity<ApiResponse<Void>> sendResetCode(@RequestBody MailRequestDto request) {
        String code = generateRandomCode();
        mailService.sendCode(request.getEmail(), code);

        mailVerificationService.saveVerificationCode(request.getEmail(), code);

        return ResponseEntity.ok(new ApiResponse<>(true, "인증 코드가 전송되었습니다.", null));
    }


    @PostMapping("/email/verify")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody MailRequestDto request) {
        boolean verified = mailVerificationService.verifyCode(request.getEmail(), request.getCode());

        if (verified) {
            mailVerificationService.removeCode(request.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(true, "인증 성공!", null));
        } else {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "인증 코드가 일치하지 않습니다.", null));
        }
    }

    @PatchMapping("/delete-account")
    public ResponseEntity<ApiResponse<Void>> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok(new ApiResponse<>(true, "회원 탈퇴 완료", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserSearchResponseDto>> searchUser(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user != null) {
            UserSearchResponseDto response = new UserSearchResponseDto(user.getNickname(), user.getProfileImg());
            return ResponseEntity.ok(new ApiResponse<>(true, "유저 검색 성공", response));
        } else {
            return ResponseEntity.status(404)
                .body(new ApiResponse<>(false, "해당 유저를 찾을 수 없습니다.", null));
        }
    }

    private String generateRandomCode() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}
