package com.rental_web.service;

import com.rental_web.domain.Renboard;
import com.rental_web.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface RenboardService {

    Long register(RenboardDTO renboardDTO);

    RenboardDTO readOne(Long rennum);

    void modify(RenboardDTO renboardDTO);

    void remove(Long rennum);

    PageResponseDTO<RenboardDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<RenboardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<RenboardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    default Renboard dtoToEntity(RenboardDTO renboardDTO){

        Renboard renboard = Renboard.builder()
                .rennum(renboardDTO.getRennum())
                .rentitle(renboardDTO.getRentitle())
                .rencontent(renboardDTO.getRencontent())
                .renwriter(renboardDTO.getRenwriter())

                .build();

        if(renboardDTO.getFileNames() != null){
            renboardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                renboard.addImage(arr[0], arr[1]);
            });
        }
        return renboard;
    }

    default RenboardDTO entityToDTO(Renboard renboard) {

        RenboardDTO renboardDTO = RenboardDTO.builder()
                .rennum(renboard.getRennum())
                .rentitle(renboard.getRentitle())
                .rencontent(renboard.getRencontent())
                .renwriter(renboard.getRenwriter())
                .regDate(renboard.getRegDate())
                .modDate(renboard.getModDate())
                .build();

        List<String> fileNames =
                renboard.getImageSet().stream().sorted().map(renboardImage ->
                        renboardImage.getUuid()+"_"+renboardImage.getFileName()).collect(Collectors.toList());

        renboardDTO.setFileNames(fileNames);

        return renboardDTO;
    }
}
