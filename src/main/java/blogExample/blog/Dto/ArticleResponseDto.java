package blogExample.blog.Dto;

import blogExample.blog.domain.Article;
import lombok.Data;
import lombok.Getter;

@Getter
public class ArticleResponseDto {

    private final String title;
    private final String content;

    public ArticleResponseDto(Article article)
    {
        this.title=article.getTitle();
        this.content=article.getContent();
    }

}
