package pingpong.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDiary {
	private int id;
	private int userId;
	private int diaryId;
	private UserDiaryRole role; 
	private String status;
}
