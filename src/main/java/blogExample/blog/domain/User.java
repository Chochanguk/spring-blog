package blogExample.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails { //UserDetails를 상속 받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    //Ouath2 기능을 위해 nullable=true로 설정
    @Column(name = "password")
    private String password;

    /**
     * OAuth 관련 키 저장 코드 추가
     */
    @Column(name="nickname",unique = true)
    private String nickname;

    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname=nickname; //생성자 nickname 추가
    }
    /**
     * 사용자 이름 변경
     */
    public User update(String nickname)
    {
        this.nickname=nickname;
        return this;
    }


    /**
     *인증 메서드
     */
    
    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }
    //사용자의 id(이메일 형식)를 반환(고유값)
    @Override
    public String getUsername() {
        return email;
    }
    
    //사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }
    
    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인 하는 로직이다.
        return true; //true: 만료가 되지 않음(아직 유효하다)
    }
    
    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //계정 잠금이 안도;ㅁ
    }
    
    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //만료 되지않음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //계정 사용 가능
    }
}