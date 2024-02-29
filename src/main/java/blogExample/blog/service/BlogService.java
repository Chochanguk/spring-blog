package blogExample.blog.service;

import blogExample.blog.Dto.AddArticleRequestDto;
import blogExample.blog.Dto.UpdateArticleRequest;
import blogExample.blog.domain.Article;
import blogExample.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service //빈으로 등록
@Transactional(readOnly = true)
public class BlogService {
    private final BlogRepository blogRepository;

    //비즈니스 로직:
    // 1. 블로그 글 추가하기
    // 2. 블로그 글 목록 조회하기
    // 3. 블로그 글 단건 조회하기


    //1. 블로그 글 추가 메서드
    @Transactional
    public Article save(AddArticleRequestDto request)
    {
        return blogRepository.save(request.toEntity());
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
    //4. 블로그 글 삭제하기
    @Transactional
    public void delete(Long id)
    {
        blogRepository.deleteById(id);
    }
    //5.블로그 업데이트 하기
    @Transactional
    public Article update(Long id, UpdateArticleRequest request)
    {
        Article article=blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Not found: "+id));

        article.update(request.getTitle(),request.getContent());
        return article;
    }


}
