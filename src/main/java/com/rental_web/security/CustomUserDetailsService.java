package com.rental_web.security;

import com.rental_web.domain.Member;
import com.rental_web.repository.MemberRepository;
import com.rental_web.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

//    private PasswordEncoder passwordEncoder;
//
//    public CustomUserDetailsService() {
//
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        Member member = result.get();

        // 사용자의 권한 정보를 가져와 "ROLE_" 접두사를 추가한 후 SimpleGrantedAuthority로 변환
        //Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getMemberid()));

        MemberSecurityDTO memberSecurityDTO =
                new MemberSecurityDTO(
                        member.getMemberid(),
                        member.getMemberpass(),
                        member.getMembername(),
                        member.getMemberemail(),
                        member.getMemberphone(),
                        member.getMemberaddr(),
                        member.getAsdate(),
                        false,
                        member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority
                                ("Role_"+memberRole.name())).collect(Collectors.toList())
                );

        log.info("memberSecurityDTO" + memberSecurityDTO);

        return memberSecurityDTO;
    }


}
