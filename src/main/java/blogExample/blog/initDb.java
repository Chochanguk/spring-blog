package blogExample.blog;

import blogExample.blog.domain.Article;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;
    //@PostConstruct: 이 어노테이션은 해당 메소드가 클래스의 객체가 생성된 후에 자동으로 호출되어야 함을 나타낸다.
    // 따라서 init() 메소드는 dbInit 클래스의 객체가 생성된 후에 자동으로 호출됨
    //
    @PostConstruct
    public void init()
    {
        initService.dbInit1();
    }

    /**
     * 아래 클래스는 데이터베이스 초기화 작업을 수행
     */
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        //제목 1, 내용 1
        public void dbInit1()
        {
            Article article1=new Article("제목 1","내용 1");
            em.persist(article1);
            Article article2=new Article("제목 2","내용 2");
            em.persist(article2);
            Article article3=new Article("제목 3","내용 3");
            em.persist(article3);
        }
    }


}
