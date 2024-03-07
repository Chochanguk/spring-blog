package blogExample.blog.Dto;

import blogExample.blog.domain.Article;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 필드를 생성자로 받음.(+a, Required는 final 지시어가 붙어야함.)
@Getter
public class AddArticleRequestDto {
    //제목 1~10자
    @NotNull
    @Size(min = 1, max = 10)
    private String title;

    @NotNull
    private String content;

    //콘트롤러에서 요청한 Body를 받을 객체 생성
    //DTO를 엔티티로 변환함.(DTO는 단순히 데이터 전달 객체임)
    public Article toEntity(String author)
    {   
        //생성자로 엔티티 세팅
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)//author 값도 추가 저장하도록 변경
                .build();

    }

}
