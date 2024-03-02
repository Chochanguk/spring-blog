package blogExample.blog.Dto;

import lombok.*;

@Getter
@AllArgsConstructor //모든 필드를 생성자로 만듦
public class CreateAccessTokenResponse {
    private String accessToken;

}
