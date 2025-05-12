package pingpong.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDaily {
    private int id;
    private int userId;
    private int dailyId;
    private String role;
}
