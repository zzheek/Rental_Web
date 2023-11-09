package com.rental_web.service;

import com.rental_web.domain.Ascal;
import com.rental_web.dto.AscalDTO;
import com.rental_web.repository.AscalRepository;
import jdk.jfr.Label;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AscalServiceImpl implements AscalService {

    private final AscalRepository ascalRepository;

    private final ModelMapper modelMapper;
    @Override
    public Long register(AscalDTO ascalDTO){
        Ascal ascal = modelMapper.map(ascalDTO, Ascal.class);

        Long ascalnum = ascalRepository.save(ascal).getAscalnum();
        log.info("IMPL@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info(ascal);

        return ascalnum;
    }

    @Override
    public List<AscalDTO> getAllEvents(){

    List<Ascal> events = ascalRepository.findAll();

    return events.stream()
            .map(event -> modelMapper.map(event, AscalDTO.class))
            .collect(Collectors.toList());
    }

}
