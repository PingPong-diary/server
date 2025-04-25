package pingpong.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwRequestDto {
	private String email;
	private String newPw;
}
