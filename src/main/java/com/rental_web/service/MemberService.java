package com.rental_web.service;

import com.rental_web.dto.MemberJoinDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.internal.Errors;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface MemberService {


    Map<String, String> validateHandling(BindingResult bindingResult);

    static class MemberIdExistException extends Exception {
        // 아이디가 존재하는 경우 MemberRepository의 save()는 inser가 아니라 update
    }

    @Transactional
    void join(MemberJoinDTO memberJoinDTO) throws MemberIdExistException;

    MemberJoinDTO getMember(String memberid);

    void modify(String memberid, MemberJoinDTO modifiedMemberDTO);

    void modifyPassword(String memberid, String newPassword);



}
