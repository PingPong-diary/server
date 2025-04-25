package pingpong.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSearchResponseDto {
	private String nickname;
	private String profileImg;
}
