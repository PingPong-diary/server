package pingpong.server.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.User;
import pingpong.server.dto.ApiResponse;
import pingpong.server.dto.KakaoUserInfoDto;
import pingpong.server.dto.response.LoginResponseDto;
import pingpong.server.service.AuthService;
import pingpong.server.service.KakaoOauthService;
import pingpong.server.service.UserService;
import pingpong.server.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/sns")
public class AuthSnsController {

    private final UserService userService;
    private final AuthService authService;
    private final KakaoOauthService kakaoOauthService;
    private final JwtUtil jwtUtil;

    @PostMapping("/kakao")
    public ResponseEntity<ApiResponse<LoginResponseDto>> kakaoLogin(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");

        KakaoUserInfoDto kakaoUser = kakaoOauthService.getUserInfo(code);
        String email = kakaoUser.getEmail();
        String nickname = kakaoUser.getNickname();

        User user = authService.getUser(email);
        if (user == null) {
            userService.joinSnsUser(email, nickname, "kakao");
        }

        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "카카오 로그인 성공", new LoginResponseDto(token, nickname, email))
        );
    }
}