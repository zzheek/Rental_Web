package com.rental_web.controller;

import com.rental_web.domain.Renboard;
import com.rental_web.dto.PageRequestDTO;
import com.rental_web.dto.PageResponseDTO;
import com.rental_web.dto.RenboardDTO;
import com.rental_web.dto.RenboardListAllDTO;
import com.rental_web.service.RenboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/")
public class IndexController {

    private final RenboardService renboardService;
    @GetMapping
    public String index(Model model) {

    List<RenboardDTO> listAll = renboardService.findAll();

    model.addAttribute("listAll",listAll);

    log.info("++++++++++++++");
    log.info(listAll);
    log.info("++++++++++++++");
    return "index";
    }
}
