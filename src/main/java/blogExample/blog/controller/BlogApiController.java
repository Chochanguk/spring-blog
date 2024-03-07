package blogExample.blog.controller;

import blogExample.blog.Dto.AddArticleRequestDto;
import blogExample.blog.Dto.AddArticleResponseDto;
import blogExample.blog.Dto.ArticleResponseDto;
import blogExample.blog.Dto.UpdateArticleRequest;
import blogExample.blog.domain.Article;
import blogExample.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController //HTTP response Bodyd에 객체 데이터를 JSON으로 변환하는 콘트롤러
public class BlogApiController {
    private final BlogService blogService;

    // DTO로 받은 후 엔티티로 변환 후 저장하기
    //1. 글 작성하기 
    @PostMapping("/api/articles")
    public ResponseEntity<AddArticleResponseDto> addArticle(
            @RequestBody AddArticleRequestDto addArticleRequestDto
            )
    {
        //요청에 응답하여 블로그 글 생성
        Article saveArticle=blogService.save(addArticleRequestDto);
        //요청된 자원이 성공적으로 생성 되었으며
        // 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddArticleResponseDto(saveArticle));

        /** http 상태 응답 코드
         *  OK(200): 요청이 성공적으로 수행됨
         *  CREATED(201) : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
         */
    }

    //2. 블로그 글 목록 조회하기
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponseDto>> findAllAriticles()
    {
        List<ArticleResponseDto> articles=blogService.findAll()
                .stream()
                .map(ArticleResponseDto::new)
                .toList();
        return ResponseEntity.ok()
                .body(articles);
    }
    //3. 블로그 글 단건 조회하기
    @GetMapping("/api/articles/{id}")
    public  ResponseEntity<ArticleResponseDto> findArticle(
            @PathVariable("id") Long id
    )
    {
        Article article=blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponseDto(article));
    }

    //4. 블로그 글 삭제하기
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id)
    {
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }
    //5. 블로그 글 수정하기
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<AddArticleResponseDto> updateArticle(
            @PathVariable("id") Long id,
            @RequestBody UpdateArticleRequest request)
    {
        Article updateArticle = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(new AddArticleResponseDto(updateArticle));
    }

}
