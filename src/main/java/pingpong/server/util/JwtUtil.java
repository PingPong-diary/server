package pingpong.server.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;

@Component
//@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	 private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

	// 토큰 발급
	public String generateToken(String email) {
		long now = System.currentTimeMillis();
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + EXPIRATION_TIME)) // 1일 유효
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	// 토큰에서 이메일 꺼내기
	public String getToken(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	// 토큰 유효성 검사
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
