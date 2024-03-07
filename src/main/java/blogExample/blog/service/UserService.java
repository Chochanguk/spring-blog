package blogExample.blog.service;

import blogExample.blog.Dto.AddUserRequest;
import blogExample.blog.domain.User;
import blogExample.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        /**
         * 직접 생성해서 패스워드를 암호화할 수 있게 코드를 수정
         */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //추가

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // ❶ 패스워드 암호화
                .password(encoder.encode(dto.getPassword())) //encoder 로 수정
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
    /**
     * 추가
     */
    //findByEmail() 메서드는 이메일을 입력받아 users 테이블에서 유저를 찾고, 없으면 예외를 발생시킴
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
    //OAuth2에서 제공하는 이메일은 유일 값이므로 해당 메서드를 사용해 유저를 찾을 수 있다.
}