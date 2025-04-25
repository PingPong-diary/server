//package pingpong.server.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import pingpong.server.domain.User;
//import pingpong.server.dto.request.JoinRequestDto;
//import pingpong.server.dto.request.LoginRequestDto;
//import pingpong.server.service.UserService;
//import pingpong.server.util.JwtUtil;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/friend")
//public class FriendController {
//
//	private final UserService userService;
//	private final JwtUtil jwtUtil;
//	private final PasswordEncoder passwordEncoder;
//
//	@GetMapping("/list")
//	public ResponseEntity<String> joinUser(@RequestBody JoinRequestDto request) {
//		if (userService.isEmail(request.getEmail())) {
//			return ResponseEntity.badRequest().body("이미 사용 중인 이메일입니다.");
//		}
//		
//		userService.joinUser(request);
//		return ResponseEntity.ok("회원가입 완료");
//	}
//	
//	@PostMapping("/request")
//	public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
//		boolean isDuplicate = userService.isEmail(email);
//		return ResponseEntity.ok(isDuplicate);
//	}
//	
//	@PostMapping("/response")
//	public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
//		User user = userService.getUser(request.getEmail());
//		if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//			String token = jwtUtil.generateToken(user.getEmail());
//			return ResponseEntity.ok(token); // 클라이언트에게 토큰 반환
//		} else {
//			return ResponseEntity.status(401).body("이메일 또는 비밀번호가 틀렸습니다.");
//		}
//	}
//	
//	@GetMapping("/delete")
//	public ResponseEntity<User> getLoginUser() {
//	    User user = userService.getLoginUser();
//	    return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(401).build(); // 인증된 사용자 반환
//	}
//}