package pingpong.server.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.User;
import pingpong.server.dto.request.JoinRequestDto;
import pingpong.server.dto.request.ResetPwRequestDto;
import pingpong.server.dto.response.ChangePwRequestDto;
import pingpong.server.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public void joinUser(JoinRequestDto request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider("local");

        userMapper.joinUser(user);
    }

    public void joinSnsUser(String email, String nickname, String provider) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setProvider(provider);
        user.setPassword(null); 

        userMapper.joinUser(user);
    }

    public boolean isEmail(String email) {
        return userMapper.isEmail(email);
    }

    public void changePassword(ChangePwRequestDto request) {
        User loginUser = authService.getLoginUser();
        if (loginUser == null) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        User dbUser = authService.getUser(loginUser.getEmail());
        if (!passwordEncoder.matches(request.getCurrentPw(), dbUser.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedNewPw = passwordEncoder.encode(request.getNewPw());
        userMapper.changePw(loginUser.getEmail(), encodedNewPw);
    }

    public void resetPassword(ResetPwRequestDto request) {
        User user = authService.getUser(request.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }

        String encodedPw = passwordEncoder.encode(request.getNewPw());
        userMapper.changePw(request.getEmail(), encodedPw);
    }

    public void deleteUser() {
        User loginUser = authService.getLoginUser();
        if (loginUser != null) {
            userMapper.deleteUser(loginUser.getEmail());
        }
    }
}
