package blogExample.blog.controller;

import blogExample.blog.Dto.AddArticleRequestDto;
import blogExample.blog.Dto.UpdateArticleRequest;
import blogExample.blog.domain.Article;
import blogExample.blog.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //스프링 부트 테스트
@AutoConfigureMockMvc //MockMvc 생성
public class BlogApiControllerTest {

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
    BlogRepository blogRepository;

    @BeforeEach //테스트 실행전 실행하는 메서드
    public void mockMvcSetUp()
    {
        //mockMvc 설정
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
        //리포지토리 초기화
        blogRepository.deleteAll();
    }



    /**
     *     테스트 로직 시작
     */

    //1. 글 작성하는 API 테스트
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception{
        //given: 블로그 글 추가에 필요한 요청 객체를 만든다.
        String url="/api/articles";
        String title="title";
        String content="content";
        AddArticleRequestDto userRequest=new AddArticleRequestDto(title,content);
        //객체 JSON으로 직렬화 (객체를 JSON으로 변환)
        //writeValueAsString: 객체를 JSON으로 직렬화
        String requestBody=objectMapper.writeValueAsString(userRequest);


        //when: 블로그 글 추가 API 요청을 보낸다. 이때 요청 타입은 JSON이면,given절에서 미리 만들어둔
        // 객체를 요청 본문으로 함께 보낸다.
        // (Mockmvc(HTTP메서드,URL,요청 타입, 요청 타입 설정)를 활용하여
        // url을 post로 JSON으로 요청 전송.)
        ResultActions result=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        //then
        //응답 코드가 Created인지 확인
        result.andExpect(status().isCreated());

        List<Article> articles=blogRepository.findAll();

        //검증 (저장된것이 1개인가, 제목이 같은가, 내용이 같은가)
        assertThat(articles.size()).isEqualTo(1); //크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    //2. 글 목록 조회 API 테스트
    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        //given
        //블로그 글을 저장
        String url="/api/articles";
        String title="title";
        String content="content";

        //블로그 글인 Article을 db에 저장.)
        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        //목록 조회 API 호출
        ResultActions resultActions= mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));
        //then
        //응답이 Ok이고, 반환받은 값 중에 0번쩨 요소의 content와 title이 저장된 값과 같은지 확인.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));

    }
    //3. 글 단건 조회 API 테스트
    @DisplayName("findAllArticles: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given
        String url="/api/articles/{id}";
        String title="title";
        String content="content";

        // 블로그 글 저장
        Article savedArticle=
        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        //저장된 블로그 글의 id값으로 API 호출
        ResultActions resultActions= mockMvc.perform(get(url,savedArticle.getId()));

        //then
        //응답이 Ok이고, 반환받은 값 중에 0번쩨 요소의 content와 title이 저장된 값과 같은지 확인.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

    }

    //4. 블로그 글 삭제하기
    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        //given
        String url="/api/articles/{id}";
        String title="title";
        String content="content";

        //블로그 글을 저장
        Article savedArticle=
                blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

        //when
        //저장한 블로그글의 id값으로 삭제 API를 호출
        mockMvc.perform(delete(url,savedArticle.getId()))
                        .andExpect(status().isOk());

        //then
        //응답이 Ok이고, 블로그 글 리스트를 전체 조회에 조회한 배열크기가 0인지 확인
        List<Article> articles=blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    //4. 블로그 글 삭제하기
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        //given
        String url="/api/articles/{id}";
        String title="title";
        String content="content";

        //블로그 글 저장
        Article savedArticle=
                blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());
        
        //블로그 글 수정에 필요한 요청 객체 생성
        String newTitle="new Title";
        String newContent="new Content";
        //수정 요청 DTO 객체
        UpdateArticleRequest request=new UpdateArticleRequest(newTitle,newContent);

        //when
        //UPDATE API로 수정요청을 보낸다. 이때 요청 타입 JSON이면
        //given 절에서 미리 만들어둔 객체를 요청 본문으로 보냄
        ResultActions resultActions=
        mockMvc.perform(patch(url,savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        //응답코드가 Ok인지 확인
        resultActions.andExpect(status().isOk());

        //블로그글 id로 조회한 후에 값이 수정되었는지 확인
        Article article=blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }
}