package blogExample.blog.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 필드를 생성자로 받음.(+a, Required는 final 지시어가 붙어야함.)
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
}
