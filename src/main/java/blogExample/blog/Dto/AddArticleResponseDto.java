package blogExample.blog.Dto;

import blogExample.blog.domain.Article;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
public class AddArticleResponseDto {

    private final Long id;
    private final String title;
    private final String content;

    public  AddArticleResponseDto(Article article)
    {
        this.id=article.getId();
        this.title=article.getTitle();
        this.content=article.getContent();
    }

}