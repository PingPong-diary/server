package pingpong.server.domain;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Friend {
    private int id;
    private int userId;
    private int friendId;
    private String status;
    private Timestamp createAt;
    private Timestamp updateAt;
}
