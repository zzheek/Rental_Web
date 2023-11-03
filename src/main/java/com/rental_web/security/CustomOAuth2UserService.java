package com.rental_web.security;

import com.rental_web.domain.Member;
import com.rental_web.domain.MemberRole;
import com.rental_web.repository.MemberRepository;
import com.rental_web.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("userRequest...");
        log.info(userRequest);

        log.info("oauth2 user...........");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("NAME:" + clientName);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String,Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }


        return oAuth2User;
    }

    private MemberSecurityDTO generateDTO(String memberemail, Map<String, Object> params) {

        Optional<Member> result = memberRepository.findByMemberemail(memberemail);

        // 데이터베이스에 해당 이메일을 사용자가 없다면
        if(result.isEmpty()) {
            // 회원추가 // memberid는 이메일주소/패스워드는 1111
            Member member = Member.builder()
                    .memberid(memberemail)
                    .memberpass(passwordEncoder.encode("1111"))
                    .membername("")
                    .memberemail(memberemail)
                    .memberphone(01011111111L)
                    .memberaddr("")
                    .asdate(null)
                    .social(true)
                    .build();
            member.addRole(MemberRole.USER);
            memberRepository.save(member);

            //MemberSecurityDTO 구성 및 반환
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(memberemail,
                            "1111",
                            "",
                            memberemail,
                            01011111111L,
                            "",
                            null,
                            true,
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberSecurityDTO.setProps(params);

            return memberSecurityDTO;
        }else {
            Member member = result.get();
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

            return memberSecurityDTO;
        }
    }


    private String getKakaoEmail(Map<String, Object> paramMap) {

        log.info("KAKAO----------");

        Object value = paramMap.get("kakao_account");

        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String)accountMap.get("email");

        log.info("email...." + email);

        return email;
    }
}
