package blogExample.blog.service;

import blogExample.blog.config.jwt.TokenProvider;
import blogExample.blog.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    
    public String createNewAccessToken(String refreshToken) {
        // 전달받은 리프레시 토큰으로 토큰 유효성검사를 진행하고
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }
        // 유효한 토큰일 때 리프레시 토큰으로 사용자 ID를 찾는다.
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        //사용자에게 토큰제공자의 토큰 생성 메서드를 통해 새로운 액세스 토큰을 생성해서 제공
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
