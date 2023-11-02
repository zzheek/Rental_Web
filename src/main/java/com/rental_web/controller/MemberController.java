package com.rental_web.controller;

import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    // 의존성 주입
    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login get.....");
        log.info("logout: " + logout);

        if(logout != null) {
            log.info("user logout...");
        }

    }


    @GetMapping("/join")
    public void joinGET(String error, String logout) {
        log.info("join get.....");

    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post.....");

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MemberIdExistException e) {
            redirectAttributes.addFlashAttribute("error", "memberid");
            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/member/login";
    }
}
