package pingpong.server.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 비어있을 수 없습니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password; 
}
