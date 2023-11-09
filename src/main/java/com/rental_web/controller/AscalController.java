package com.rental_web.controller;

import com.rental_web.dto.AscalDTO;
import com.rental_web.repository.AscalRepository;
import com.rental_web.service.AscalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/ascal")
@Log4j2
@RequiredArgsConstructor
public class AscalController {

    private final AscalService ascalService;

    @GetMapping("/main")
    public String main(Model model) {
        List<AscalDTO> events = ascalService.getAllEvents();

        model.addAttribute("events",events);
        return "/";
    }
    @PostMapping
    public Map<String, Long> register(
            @Valid @RequestBody AscalDTO ascalDTO,
            BindingResult bindingResult) throws BindException {

        log.info(ascalDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long ascalnum = ascalService.register(ascalDTO);

        resultMap.put("ascalnum", ascalnum);

        log.info("CT@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        log.info(ascalnum);
        log.info("CT@resultMap@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info(resultMap);

        return resultMap;
    }


}
