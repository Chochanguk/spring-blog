package blogExample.blog.config.jwt;


import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class JwtFactory {

    private String subject = "test@email.com";

    private Date issuedAt = new Date(); //객체가 생성된 시점
    //현재 시간으로부터 14일후로 설정
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());

    private Map<String, Object> claims = emptyMap();

    // 빌더 패턴을 사용해 설정이 필요한 데이터만 선택 설정
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration,
                      Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }
    //기본값을 가지는 JwtFactory 객체를 생성하기 위한 정적 메서드
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    // jwt 라이브러리를 사용해 JWT 토큰 생성
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject) //토큰의 주제(=토큰 발급 받은 사용자의 식별자)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // jWT의 헤더 타입 설정
                .setIssuer(jwtProperties.getIssuer()) //JWT 토큰의 발급자
                .setIssuedAt(issuedAt) //Jwt 토큰 발행시간
                .setExpiration(expiration) //jwt 토큰 만료 시간
                .addClaims(claims) // 클레임 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())// 토큰에 서명 추가시 알고리즘 선택 및 비밀키 선정
                .compact();// JWT 토큰을 생성하고 문자열 형태로 반환
    }
}