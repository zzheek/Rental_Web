package com.rental_web.service;

import com.rental_web.dto.MemberJoinDTO;

public interface MemberService {


    static class MemberIdExistException extends Exception {
        // 아이디가 존재하는 경우 MemberRepository의 save()는 inser가 아니라 update
    }

    void join(MemberJoinDTO memberJoinDTO) throws MemberIdExistException;

    MemberJoinDTO getMember(String memberid);

    void modify(String memberid, MemberJoinDTO modifiedMemberDTO);

    void modifyPassword(String memberid, String newPassword);



}
