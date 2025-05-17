package pingpong.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pingpong.server.domain.User;
import pingpong.server.dto.request.JoinRequestDto;
import pingpong.server.dto.request.ResetPwRequestDto;
import pingpong.server.dto.response.ChangePwRequestDto;
import pingpong.server.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User getUser(String email) {
	    if (email == null || email.isBlank()) {
	        throw new IllegalArgumentException("이메일이 비어 있습니다.");
	    }
	    return userMapper.getUser(email);
	}


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

	public User getLoginUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null) return null;

	    Object principal = authentication.getPrincipal();
	    if (principal instanceof User user) {
	        return user;
	    }
	    return null;
	}


	public void changePassword(ChangePwRequestDto request) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null) {
	        throw new IllegalStateException("인증 정보가 없습니다.");
	    }

	    Object principal = auth.getPrincipal();
	    if (!(principal instanceof User user)) {
	        throw new IllegalStateException("유저 정보가 없습니다.");
	    }

	    User dbUser = userMapper.getUser(user.getEmail());
	    if (!passwordEncoder.matches(request.getCurrentPw(), dbUser.getPassword())) {
	        throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
	    }

	    String encodedNewPw = passwordEncoder.encode(request.getNewPw());
	    userMapper.changePw(user.getEmail(), encodedNewPw);
	}


	public void resetPassword(ResetPwRequestDto request) {
		User user = userMapper.getUser(request.getEmail());
		if (user == null) {
			throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
		}

		String encodedPw = passwordEncoder.encode(request.getNewPw());
		userMapper.changePw(request.getEmail(), encodedPw);
	}

	public void deleteUser() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null) return;

	    Object principal = auth.getPrincipal();
	    if (principal instanceof User user) {
	        userMapper.deleteUser(user.getEmail());
	    }
	}


}