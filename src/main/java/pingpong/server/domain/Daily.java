package pingpong.server.domain;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Daily {
    private int id;
    private Timestamp writtenAt;
    private String content;
}
