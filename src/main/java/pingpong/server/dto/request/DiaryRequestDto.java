package pingpong.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryRequestDto {
    private int emotionColor;
    private int weather;
    private String title;
    private String content;
}