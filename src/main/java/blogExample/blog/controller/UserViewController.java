package blogExample.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    //로그인 뷰 컨트럴러
    @GetMapping("/login")
    public String login()
    {

        return "oauthLogin";
    }
    //회원가입 뷰 컨트럴러
    @GetMapping("/signup")
    public String signup()
    {
        return "signup";
    }
}
