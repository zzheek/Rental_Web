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

//    @PostMapping("remove")
//    public ResponseEntity<String> removeEvent(@RequestBody RemoveEventDate removeEventDate){
//
//        if (deleteEvent(removeEventDate.getAscaltime())) {
//            return ResponseEntity.ok("일정이 삭제되었습니다.");
//        }else{
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이벤트 삭제 중 오류가 발생했습니다.");
//        }
//    }

    @DeleteMapping
    public ResponseEntity<String> deleteAscal(@RequestBody String ascaltime) {
        // 여기에 삭제 로직을 구현하고, 성공 여부에 따라 응답을 반환


        if (deleteEvent(ascaltime)) {
            return ResponseEntity.ok("일정이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이벤트 삭제 중 오류가 발생했습니다.");
        }
    }




}