package pingpong.server.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryResponseDto {
    private int id;
    private int emotionColor;
    private String nickname;
    private int weather;
    private String title;
    private String content;
    private boolean shared;
    private List<EmotionResponseDto> emotions;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
