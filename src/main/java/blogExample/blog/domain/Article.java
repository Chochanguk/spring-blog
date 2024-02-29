package blogExample.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

@Entity //빈으로 등록
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본생성자를 protected로 생성하는 롬복
public class Article {

    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="content",nullable = false)
    private String content;

    //setter대신 생성자로 값 설정
    @Builder //★★Builder 패턴으로 생성자 객체 정의★★★
    public Article(String title,String content)
    {
        this.title=title;
        this.content=content;
    }
    /**
     *    ★Builder 패턴:
     *
     *    기존:
     *    new Article("abc","def");
     *
     *    builder 패턴:
     *    Article.builder()
     *           .title("abc")
     *           .content("def")
     *           .build();
     *
     *    파라미터 삽입시 위치에 상관없이 주입가능
     *    (메서드가 아니라 객체에서만 사용가능)
     */

//    //기본 생성자 (롬복으로 설정
//    JPA에서는 기본 생성자를 통해 접근하기에
//    protected Article(){
//
//    }

    /**
     * 수정 메서드 작성(값을 재세팅 하기 위해서)
     */
    public void update(String title,String content)
    {
        this.title=title;
        this.content=content;
    }




}
