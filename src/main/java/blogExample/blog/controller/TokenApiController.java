package blogExample.blog.controller;

import blogExample.blog.Dto.CreateAccessTokenRequest;
import blogExample.blog.Dto.CreateAccessTokenResponse;
import blogExample.blog.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    //토큰이 요청으로 오면 새로운 리프레시 토큰을 발행해서 줌.(2시간 마다)
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
    (@RequestBody CreateAccessTokenRequest request)
    {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
