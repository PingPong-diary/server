package pingpong.server.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private int id;
	private String email;
	private String provider;
	private String password;
	private String nickname;
	private String profileImg;
	private Timestamp createAt;
	private Timestamp updateAt;
}
