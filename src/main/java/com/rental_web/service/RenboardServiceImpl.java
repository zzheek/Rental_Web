package com.rental_web.service;

import com.rental_web.domain.Renboard;
import com.rental_web.dto.*;
import com.rental_web.repository.RenboardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class RenboardServiceImpl implements RenboardService{

    private final ModelMapper modelMapper;

    private final RenboardRepository renboardRepository;

    @Override
    public Long register(RenboardDTO renboardDTO) {

        Renboard renboard = dtoToEntity(renboardDTO);

        Long rennum = renboardRepository.save(renboard).getRennum();

        return rennum;
    }

    @Override
    public RenboardDTO readOne(Long rennum) {

        Optional<Renboard> result = renboardRepository.findByIdWithImages(rennum);

        Renboard renboard = result.orElseThrow();

        RenboardDTO renboardDTO = entityToDTO(renboard);

        return renboardDTO;
    }

    @Override
    public void modify(RenboardDTO renboardDTO) {

        Optional<Renboard> result = renboardRepository.findById(renboardDTO.getRennum());

        Renboard renboard = result.orElseThrow();

        renboard.change(renboardDTO.getRentitle(),renboardDTO.getRencontent());

        renboard.clearImages();

        if (renboardDTO.getFileNames() != null) {
            for (String filename : renboardDTO.getFileNames()) {
                String[] arr = filename.split("_");
                renboard.addImage(arr[0],arr[1]);
            }
        }

        renboardRepository.save(renboard);

    }

    @Override
    public void remove(Long rennum) {
        renboardRepository.deleteById(rennum);
    }

    @Override
    public PageResponseDTO<RenboardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("rennum");

        Page<Renboard> result = renboardRepository.searchAll(types,keyword,pageable);

        List<RenboardDTO> dtoList = result.getContent().stream().map(
                renboard -> modelMapper.map(renboard,RenboardDTO.class)
                ).collect(Collectors.toList());

        return PageResponseDTO.<RenboardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<RenboardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("rennum");

        Page<RenboardListReplyCountDTO> result = renboardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<RenboardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<RenboardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("rennum");

        Page<RenboardListAllDTO> result = renboardRepository.searchWithAll(types, keyword, pageable);

        return PageResponseDTO.<RenboardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();

    }
}
