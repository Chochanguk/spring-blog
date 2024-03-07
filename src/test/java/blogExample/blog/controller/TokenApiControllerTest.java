package blogExample.blog.controller;

import blogExample.blog.Dto.CreateAccessTokenRequest;
import blogExample.blog.config.jwt.JwtFactory;
import blogExample.blog.config.jwt.JwtProperties;
import blogExample.blog.domain.RefreshToken;
import blogExample.blog.domain.User;
import blogExample.blog.repository.RefreshTokenRepository;
import blogExample.blog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {
    /**
     * 테스트 초기 설정
     */
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //직렬화,역지렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach //테스트 실행전 실행하는 메서드
    public void mockMvcSetUp()
    {
        //mockMvc 설정
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
        //리포지토리 초기화
        userRepository.deleteAll();
    }

    @DisplayName("createNewAccessToken: 새로운 엑세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception{
        /**
         * given
         */
        //0. api url
        final String url="/api/token";

        //1.사용자 예시
        User testUser = userRepository.save(User.builder()
                .email("test@gmail.com")
                .password("test")
                .build()
        );
        //2. 리프레시 토큰 발행
        String refreshToken = JwtFactory.builder()
                .claims(Map.of("id",testUser.getId()))
                .build()
                .createToken(jwtProperties);
        //3. 리프레시 토큰 저장
        RefreshToken refreshToken1 = refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToken));
        System.out.println("==================");
        System.out.println(refreshToken1);
        System.out.println("==================");
        //4. 토큰 요청에 대해 토큰을 리프레시 토큰으로 세팅
        CreateAccessTokenRequest request=new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);

        final String requestBody=objectMapper.writeValueAsString(request);
        System.out.println("==================");
        System.out.println(" requestBody = "+ requestBody);
        System.out.println("==================");
        /**
         * when
         */
        //토큰 추가 API에 요청을 보낸다. 이때 요청 타입은 json이며,given절에서 미리 만들어둔
        // 객체를 요청본문으로 함께 보낸다.
        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        /**
         * then
         */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
}