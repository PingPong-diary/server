package pingpong.server.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.User;
import pingpong.server.mapper.UserMapper;
import pingpong.server.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        return null;
    }

    public User getUser(String email) {
        return userMapper.getUser(email);
    }

    public void saveRefreshToken(String email, String refreshToken) {
        String key = "RT:" + email;
        long expiration = jwtUtil.getRefreshTokenExpiration();
        redisTemplate.opsForValue().set(key, refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    public boolean validateRefreshToken(String refreshToken) {
        String email = jwtUtil.getToken(refreshToken);
        String storedToken = redisTemplate.opsForValue().get("RT:" + email);
        return storedToken != null && storedToken.equals(refreshToken);
    }

    public String reissueAccessToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        String email = jwtUtil.getToken(refreshToken);
        return jwtUtil.generateToken(email);
    }

    public void deleteRefreshToken(String email) {
        redisTemplate.delete("RT:" + email);
    }
}
