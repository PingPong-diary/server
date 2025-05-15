package pingpong.server.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailVerificationService {
	private final StringRedisTemplate redisTemplate;

	public void saveVerificationCode(String email, String code) {
		redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(5));
	}

	public boolean verifyCode(String email, String inputCode) {
		String storedCode = redisTemplate.opsForValue().get(email);
		return storedCode != null && storedCode.equals(inputCode);
	}

	public void removeCode(String email) {
		redisTemplate.delete(email);
	}
}
