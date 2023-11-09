package com.rental_web.service;

import com.rental_web.dto.AscalDTO;

import java.util.List;


public interface AscalService {

    Long register(AscalDTO ascalDTO);

    List<AscalDTO> getAllEvents();

    List<AscalDTO> findAll();

}
