package blogExample.blog.controller;

import blogExample.blog.Dto.AddUserRequest;
import blogExample.blog.repository.UserRepository;
import blogExample.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    //회원가입 구현하기
    @PostMapping("/user")
    public String signup(AddUserRequest request)
    {
        userService.save(request); //회원 가입 메서드 호출
        return "redirect:/login"; //회원가입이 완료된 이후에 로그인 페이지로 이동
    }
    //로그아웃 메서드
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        new SecurityContextLogoutHandler().logout(request,response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redriect::/login";
    }
}
