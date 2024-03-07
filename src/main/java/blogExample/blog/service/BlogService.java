package blogExample.blog.service;

import blogExample.blog.Dto.AddArticleRequestDto;
import blogExample.blog.Dto.UpdateArticleRequest;
import blogExample.blog.domain.Article;
import blogExample.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service //빈으로 등록
@Transactional(readOnly = true)
public class BlogService {
    private final BlogRepository blogRepository;

    //비즈니스 로직:
    // 1. 블로그 글 추가하기
    // 2. 블로그 글 목록 조회하기
    // 3. 블로그 글 단건 조회하기
    // 4. 블로그 글 삭제하기
    // 5. 블로그 글 수정하기


    //1. 블로그 글 추가 메서드
    @Transactional
    public Article save(AddArticleRequestDto request,String userName)
    {
        /**
         * username= 글쓴이(author)
         */
        return blogRepository.save(request.toEntity(userName));
    }

    // 2. 블로그 글 목록 조회하기
    public List<Article> findAll()
    {
        return blogRepository.findAll();
    }

    //3. 블로그 글 단건 조회하기
    public Article findById(Long id)
    {
        // 만약 NULL값이면 에러처리
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Not found: "+id));
                //           ()=""= 즉, Null 이면 에러처리
    }

    /**
     *  수정하거나 삭제할 때 요청 헤더에 토큰을 전달하므로 사용자 자신이 작성한 글인지 검증함.
     *  따라서, 본인 글이 아닌데 수정, 삭제를 시도하는 경우에 예외를 발생시키도록 코드를 수정.
     */

    //4. 블로그 글 삭제하기
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    //5.수정하기
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}
