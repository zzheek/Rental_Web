package com.rental_web.controller;

import com.rental_web.domain.Renboard;
import com.rental_web.dto.*;
import com.rental_web.service.AscalService;
import com.rental_web.service.RenboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/")
public class IndexController {

    private final RenboardService renboardService;
    private final AscalService ascalService;
    @GetMapping
    public String index(Model model) {
        //최근 렌탈회사
    List<RenboardDTO> listAll = renboardService.findAll();

    model.addAttribute("listAll",listAll);

    log.info("++++++++++++++");
    log.info(listAll);
    log.info("++++++++++++++");

    //오늘의 AS일정
    List<AscalDTO> ListAscal = ascalService.findDay();

    model.addAttribute("ListAscal",ListAscal);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        for (AscalDTO ascalDTO : ListAscal) {
            LocalDateTime originalDateTime = LocalDateTime.parse(ascalDTO.getAscaltime());
            LocalTime timeOnly = originalDateTime.toLocalTime();
            ascalDTO.setAscaltime(String.valueOf(timeOnly));
        }

    log.info("@@@@@@@@@@@@");
    log.info(ListAscal);


    return "index";
    }

}
