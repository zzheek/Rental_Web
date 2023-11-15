package com.rental_web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberJoinDTO {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자리여야 합니다.")
    private String memberid;    // 회원아이디

    @NotBlank(message = "이름을 입력해주세요.")
    private String membername;  // 회원 이름

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 소문자, 숫자, 특수문자를 사용하세요.")
    private String memberpass;  // 회원 비밀번호

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String memberemail; // 회원 이메일

    @NotNull(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 10자리 또는 11자리 숫자만 입력해주세요.")
    private String memberphone;   // 회원 전화번호

    @NotBlank(message = "주소를 입력해주세요.")
    private String memberaddr;  // 회원 주소

    private LocalDateTime asdate;   // AS 날짜

    private boolean social;     // 소셜 가입 여부

    public String getMemberpass() {
        return memberpass;
    }




}
