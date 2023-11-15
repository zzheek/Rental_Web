package com.rental_web.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String memberid;    // 회원아이디
    private String membername;  // 회원 이름
    private String memberpass;  // 회원 비밀번호
    private String memberemail; // 회원 이메일
    private String memberphone;   // 회원 전화번호
    private String memberaddr;  // 회원 주소
    private LocalDateTime asdate;   // AS 날짜
    private boolean social;     // 소셜 가입 여부

    private Map<String, Object> props; // 소셜 로그인 정보

    public MemberSecurityDTO(String username, String password, String membername,
                             String memberemail, String memberphone, String memberaddr,
                             LocalDateTime asdate, boolean social,
                             Collection<? extends GrantedAuthority> authorities){

        super(username,password,authorities);

        this.memberid = username;
        this.membername = membername;
        this.memberpass = password;
        this.memberemail = memberemail;
        this.memberphone = memberphone;
        this.memberaddr = memberaddr;
        this.asdate = asdate;
        this.social = social;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.memberid;
    }

}
