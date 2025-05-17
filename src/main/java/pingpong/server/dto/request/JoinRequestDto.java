package pingpong.server.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;

	private String password;

	@NotBlank(message = "닉네임은 필수입니다.")
	private String nickname;

	private String provider;     
	private String profileImg;   
}
