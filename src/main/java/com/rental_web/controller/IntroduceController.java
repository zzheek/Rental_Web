package com.rental_web.controller;

import com.rental_web.domain.Member;
import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/introduce")
@Log4j2
@RequiredArgsConstructor
public class IntroduceController {

    @GetMapping("/introduce")
    public void introduce(Model model, Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("author", userDetails.getUsername());
        model.addAttribute("pass",userDetails.getPassword());


    }
}
