package blogExample.blog.service;

import blogExample.blog.domain.RefreshToken;
import blogExample.blog.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    //새로 만들어 전달 받은 리프레쉬 토큰으로 리프레시 토큰 객체 검색해서 전달하는 메서드
    public RefreshToken findByRefreshToken(String refreshToken)
    {
            return refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }

}
