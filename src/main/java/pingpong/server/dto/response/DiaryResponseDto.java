package pingpong.server.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryResponseDto {
    private int id;
    private int emotionColor;
    private int weather;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
