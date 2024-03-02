package blogExample.blog.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
