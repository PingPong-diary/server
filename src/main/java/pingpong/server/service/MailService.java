package pingpong.server.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	 private final JavaMailSender mailSender;

	    public void sendCode(String to, String code) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject("비밀번호 재설정 인증 코드");
	        message.setText("인증 코드: " + code);

	        mailSender.send(message);
	    }

}
