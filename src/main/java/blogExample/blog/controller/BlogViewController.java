package blogExample.blog.controller;


import blogExample.blog.Dto.ArticleListViewResponse;
import blogExample.blog.Dto.ArticleViewResponse;
import blogExample.blog.domain.Article;
import blogExample.blog.service.BlogService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller //MVC형태로 만들 예정이니
public class BlogViewController {
    private final BlogService blogService;


    @GetMapping("/articles")
    public String getArticles(Model model)
    {
        List<ArticleListViewResponse> articles=blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles",articles);

        return "articleList";

    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model)
    {
        Article ariticle=blogService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(ariticle));

        return "article";
    }



    @GetMapping("/new-article")
    //id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id,Model model)
    {
        //id가 없으면 생성
        if(id==null)
        {
            model.addAttribute("article",new ArticleViewResponse());
        }
        else{ //id가 없으면 수정
            Article article=blogService.findById(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }
        return  "newArticle";
    }

}
