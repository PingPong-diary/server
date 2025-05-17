package pingpong.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import pingpong.server.domain.User;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.request.JoinRequestDto;
import pingpong.server.dto.request.LoginRequestDto;
import pingpong.server.dto.response.LoginResponseDto;
import pingpong.server.service.UserService;
import pingpong.server.util.JwtUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Void>> joinUser(@RequestBody JoinRequestDto request) {
        if (userService.isEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(true, "이미 등록된 아이디입니다!", null));
        }
        userService.joinUser(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입 성공", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request) {
        User user = userService.getUser(request.getEmail());

        if (user != null &&
            "local".equals(user.getProvider()) &&
            user.getPassword() != null &&
            passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String token = jwtUtil.generateToken(user.getEmail());
            LoginResponseDto response = new LoginResponseDto(token, user.getNickname(), user.getEmail());

            return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", response));
        }

        return ResponseEntity.status(401)
                .body(new ApiResponse<>(false, "이메일 또는 비밀번호가 틀렸습니다.", null));
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 되었습니다. 토큰은 클라이언트에서 삭제하세요.", null));
    }
}
