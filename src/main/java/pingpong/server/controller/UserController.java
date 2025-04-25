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

import lombok.RequiredArgsConstructor;
import pingpong.server.domain.User;
import pingpong.server.dto.request.JoinRequestDto;
import pingpong.server.dto.request.LoginRequestDto;
import pingpong.server.dto.request.MailRequestDto;
import pingpong.server.dto.request.ResetPwRequestDto;
import pingpong.server.dto.response.ChangePwRequestDto;
import pingpong.server.dto.response.UserSearchResponseDto;
import pingpong.server.service.MailService;
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

	@PostMapping("/join")
	public ResponseEntity<String> joinUser(@RequestBody JoinRequestDto request) {
		if (userService.isEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("이미 사용 중인 이메일입니다.");
		}

		userService.joinUser(request);
		return ResponseEntity.ok("회원가입 완료");
	}

	@GetMapping("/check-email")
	public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
		boolean isDuplicate = userService.isEmail(email);
		return ResponseEntity.ok(isDuplicate);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
		User user = userService.getUser(request.getEmail());
		if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			String token = jwtUtil.generateToken(user.getEmail());
			return ResponseEntity.ok(token); // 클라이언트에게 토큰 반환
		} else {
			return ResponseEntity.status(401).body("이메일 또는 비밀번호가 틀렸습니다.");
		}
	}

	@GetMapping("/token")
	public ResponseEntity<User> getLoginUser() {
		User user = userService.getLoginUser();
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(401).build(); // 인증된 사용자 반환
	}

	@GetMapping("/search")
	public ResponseEntity<UserSearchResponseDto> searchUser(@RequestParam String email) {
		User user = userService.getUser(email);
		if (user != null) {
			UserSearchResponseDto response = new UserSearchResponseDto(user.getNickname(), user.getProfileImg());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/change-pw")
	public ResponseEntity<?> changePassword(@RequestBody ChangePwRequestDto request) {
		try {
			userService.changePassword(request);
			return ResponseEntity.ok("비밀번호가 변경되었습니다.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PostMapping("/pw/reset")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPwRequestDto request) {
		try {
			userService.resetPassword(request);
			return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
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

	@PostMapping("/pw/reset/request")
	public ResponseEntity<String> sendResetCode(@RequestBody MailRequestDto request) {
		String code = generateRandomCode(); 
		mailService.sendCode(request.getEmail(), code);

		return ResponseEntity.ok("인증 코드가 전송되었습니다.");
	}

}