package com.rental_web.service;

import com.rental_web.domain.Ascal;
import com.rental_web.dto.AscalDTO;
import com.rental_web.repository.AscalRepository;
import jdk.jfr.Label;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        log.info(ascal);

        return ascalnum;
    }

    @Override
    public List<AscalDTO> findAll() {
        List<Ascal> allAscal = ascalRepository.findAll();

        return allAscal.stream()
                .map(ascal -> modelMapper.map(ascal, AscalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(AscalDTO ascalDTO) {
        // AscalDTO에서 필요한 정보를 추출하여 엔터티를 찾음
        Optional<Ascal> ascal = ascalRepository.findByAscaltime(ascalDTO.getAscaltime());

        // 엔터티가 존재하면 삭제
        ascal.ifPresent(ascalRepository::delete);
    }

    @Override
    public List<AscalDTO> findDay() {
        List<Ascal> dayAscal = ascalRepository.findDay();

        return dayAscal.stream()
                .map(ascal ->modelMapper.map(ascal, AscalDTO.class))
                .collect(Collectors.toList());
    }



}
