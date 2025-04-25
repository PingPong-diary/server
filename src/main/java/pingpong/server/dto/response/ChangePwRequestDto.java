package pingpong.server.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwRequestDto {
	private String currentPw;
    private String newPw;

}
