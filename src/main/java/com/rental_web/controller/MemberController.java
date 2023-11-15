package com.rental_web.controller;


import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.internal.Errors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext()
                .getAuthentication());

        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public void joinGET(String error, String logout, Model model) {
        log.info("join get.....");
        model.addAttribute("memberJoinDTO", new MemberJoinDTO()); // 이 부분을 추가

    }

    @PostMapping("/join")
    public String joinPOST(@Valid  MemberJoinDTO memberJoinDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있을 경우
//            model.addAttribute("error", "입력값을 다시 확인해주세요.");
            model.addAttribute("memberJoinDTO", memberJoinDTO); // 입력 데이터를 모델에 추가

            Map<String, String> validatorResult = memberService.validateHandling(bindingResult);
                for (String key : validatorResult.keySet()) {
                    model.addAttribute(key, validatorResult.get(key));
                }
            return "/member/join"; // 뷰를 직접 반환
        }

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MemberIdExistException e) {
            model.addAttribute("error", "memberid");
            model.addAttribute("memberJoinDTO", memberJoinDTO); // 입력 데이터를 모델에 추가
            return "/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify(Principal principal, Model model) {

        String memberid = principal.getName();
        MemberJoinDTO dto = memberService.getMember(memberid);

        model.addAttribute("dto", dto);

        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modifyPOST(MemberJoinDTO modifiedMemberDTO, RedirectAttributes redirectAttributes) {
        // Principal을 이용해 현재 로그인한 사용자의 ID를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberid = authentication.getName();

        // 수정된 회원 정보를 저장하는 로직 추가
        memberService.modify(memberid, modifiedMemberDTO);

        // 패스워드가 수정되었을 때만 패스워드를 따로 수정
        if (modifiedMemberDTO.getMemberpass() != null && !modifiedMemberDTO.getMemberpass().isEmpty()) {
            memberService.modifyPassword(memberid, modifiedMemberDTO.getMemberpass());
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/member/modify";
    }





}
