package com.rental_web.service;

import com.rental_web.domain.Member;
import com.rental_web.domain.MemberRole;
import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = "valid_" + error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

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

    @Override
    public MemberJoinDTO getMember(String memberid) {
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new MemberNotFoundException("ID가 " + memberid + "인 회원을 찾을 수 없습니다."));

        return modelMapper.map(member, MemberJoinDTO.class);
    }

    @Override
    public void modify(String memberid, MemberJoinDTO modifiedMemberDTO) {
        Member existingMember = memberRepository.findById(memberid)
                .orElseThrow(() -> new MemberNotFoundException("ID가 " + memberid + "인 회원을 찾을 수 없습니다."));

        // 비밀번호를 수정하는 경우에만 인코딩
        if (modifiedMemberDTO.getMemberpass() != null ) {
            existingMember.changePassword(passwordEncoder.encode(modifiedMemberDTO.getMemberpass()));
        }

        // ModelMapper를 사용하여 MemberJoinDTO의 필드를 Member 엔터티에 매핑
        modelMapper.map(modifiedMemberDTO, existingMember);

        memberRepository.save(existingMember);
    }

    @Override
    public void modifyPassword(String memberid, String newPassword) {
        Member existingMember = memberRepository.findById(memberid)
                .orElseThrow(() -> new MemberNotFoundException("ID가 " + memberid + "인 회원을 찾을 수 없습니다."));

        existingMember.changePassword(passwordEncoder.encode(newPassword));

        memberRepository.save(existingMember);
    }

    public class MemberNotFoundException extends RuntimeException {
        public MemberNotFoundException(String message) {
            super(message);
        }
    }




}
