package com.rental_web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/ascal")
@Log4j2
@RequiredArgsConstructor
public class AscalController {

    @GetMapping("/main")
    public void main() {

    }
}
