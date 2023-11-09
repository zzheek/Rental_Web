package com.rental_web.controller;

import com.rental_web.domain.Ascal;
import com.rental_web.dto.AscalDTO;
import com.rental_web.repository.AscalRepository;
import com.rental_web.service.AscalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/ascal")
@Log4j2
@RequiredArgsConstructor
public class AscalController {

    private final AscalService ascalService;

//    @GetMapping("/main")
//    @ResponseBody
//    public List<Map<String, Object>> main() {
//        List<AscalDTO> listAll = ascalService.findAll();
//
//        List<Map<String, Object>> result = new ArrayList<>();
//
//        for (AscalDTO ascalDTO : listAll) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("ascalnum", ascalDTO.getAscalnum());
//            map.put("ascalwriter", ascalDTO.getAscalwriter());
//            map.put("ascaltime", ascalDTO.getAscaltime());
//            map.put("ascalText", ascalDTO.getAscalText());
//
//            result.add(map);
//        }
//
//
//        return result;
//
//    }

    @GetMapping("/main")
    public void main(Model model) {

        List<AscalDTO> listAll = ascalService.findAll();


        log.info("+@+@+@+@+@+@+@+@+@+@+");
        log.info(listAll);

        model.addAttribute("ascalList", listAll);

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


        return resultMap;
    }


}