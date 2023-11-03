package com.rental_web.service;

import com.rental_web.domain.Member;
import com.rental_web.domain.MemberRole;
import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MemberIdExistException {

        String memberid = memberJoinDTO.getMemberid();

        boolean exist = memberRepository.existsById(memberid);

        if (exist) {
            throw new MemberIdExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMemberpass()));
        member.addRole(MemberRole.USER);

        memberRepository.save(member);

    }
}
