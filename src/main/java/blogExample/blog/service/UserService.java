package blogExample.blog.service;

import blogExample.blog.Dto.AddUserRequest;
import blogExample.blog.domain.User;
import blogExample.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//UserDto
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //패스 워드 저장시 시큐리티를 설정하며 패스워드 인코딩용으로 등록한 빈을 사용해서 암호화 한 후에 저장
    public Long save(AddUserRequest dto)
    {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                //패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build())
                .getId();
    }
    //리프레시 토큰을 전달받아 토큰 제공자를 사용해 새로운 액세스 토큰을 만드는 토큰 서비스 클래스 생성.

    //findById 구현-> 유저 객체 반환
    public User findById(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}
