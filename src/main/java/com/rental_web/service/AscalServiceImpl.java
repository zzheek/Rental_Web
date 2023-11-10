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
    public List<AscalDTO> getAllEvents(){

    List<Ascal> events = ascalRepository.findAll();

    return events.stream()
            .map(event -> modelMapper.map(event, AscalDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<AscalDTO> findAll() {
        List<Ascal> allAscal = ascalRepository.findAll();

        return allAscal.stream()
                .map(ascal -> modelMapper.map(ascal, AscalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAscal(String ascaltime) {
        Optional<Ascal> optionalAscal = ascalRepository.findByAscaltime(ascaltime);
        optionalAscal.ifPresent(ascal -> ascalRepository.delete(ascal));
    }
    // Entity를 DTO로 변환하는 메서드
    private AscalDTO convertToDTO(Ascal ascal) {
        AscalDTO ascalDTO = new AscalDTO();
        ascalDTO.setAscalnum(ascal.getAscalnum());
        ascalDTO.setAscalwriter(ascal.getAscalwriter());
        ascalDTO.setAscaltime(ascal.getAscaltime());
        ascalDTO.setAscalText(ascal.getAscalText());
        return ascalDTO;
    }


}
