package com.rental_web.controller;

import com.rental_web.domain.Member;
import com.rental_web.dto.MemberJoinDTO;
import com.rental_web.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/introduce")
@Log4j2
@RequiredArgsConstructor
public class IntroduceController {

    @GetMapping("/introduce")
    public void introduce() throws NullPointerException {

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        model.addAttribute("author", userDetails.getUsername());
//        model.addAttribute("pass",userDetails.getPassword());
//        log.info("========================authentication.isAuthenticated()================================");
//        log.info(authentication);
//        log.info("========================((UserDetails) authentication.getPrincipal()).getAuthorities()================================");
//        log.info(((UserDetails) authentication.getPrincipal()).getAuthorities());

    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e) {

        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg",  "No Such Element Exception");
        return ResponseEntity.badRequest().body(errorMap);
    }
}
