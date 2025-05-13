package pingpong.server.domain;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diary {
	private int id;
	private int ownerId;
	private String title;
	private String content;
	private int emotionColor;
	private int weather;
	private int isDeleted;
	private Timestamp createAt;
	private Timestamp updateAt;
}
