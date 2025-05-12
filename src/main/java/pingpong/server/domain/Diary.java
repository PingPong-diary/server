package pingpong.server.domain;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diary {
    private int id;
    private Timestamp writtenAt;
    private int emotionColor;
    private int weather;
    private String title;
    private String content;
}
