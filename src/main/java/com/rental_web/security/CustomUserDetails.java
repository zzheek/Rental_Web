package com.rental_web.security;

import com.rental_web.domain.Member;
import com.rental_web.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Spring security에 있는 UserDetails를 구현
//Spring Security에서 사용자의 정보를 담아줌
public class CustomUserDetails implements UserDetails {

    //member 엔티티 사용
    private final Member member;

    public CustomUserDetails(Member member){
        this.member = member;
    }
    //생성자
    public Member getMember(){
        return member;
    }

    //member 계정의 권한을 담아둠
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRoleSet().toString()));
        return authorities;
    }

    //member 계정 비밀번호 담아둠
    @Override
    public String getPassword() {
        return member.getMemberpass();
    }

    //member 계정 아이디 담아둠
    @Override
    public String getUsername() {
        return member.getMemberid();
    }

    // 계정 만료 여부 ( true : 만료안됨 )
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 ( true : 잠기지 않음 )
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 만료 여부 ( true : 만료안됨 )
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 ( true : 활성화됨 )
    @Override
    public boolean isEnabled() {
        return true;
    }
}
