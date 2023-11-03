package com.rental_web.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member{

    @Id
    private String memberid;    // 회원아이디

    private String membername;  // 회원 이름

    private String memberpass;  // 회원 비밀번호

    private String memberemail; // 회원 이메일

    private Long memberphone;   // 회원 전화번호

    private String memberaddr;  // 회원 주소

    private LocalDateTime asdate;   // AS 날짜

    private boolean social;     // 소셜 가입 여부

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String memberpass) {
        this.memberpass = memberpass;
    }

    public void changeEmail(String memberemail) {
        this.memberemail = memberemail;
    }

    public void changePhone(Long memberphone) {
        this.memberphone = memberphone;
    }

    public void changeAddr(String memberaddr) {
        this.memberaddr = memberaddr;
    }

    public void clearRoles() { this.roleSet.clear(); }

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

}
