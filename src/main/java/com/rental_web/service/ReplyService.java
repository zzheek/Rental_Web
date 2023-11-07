package com.rental_web.service;

import com.rental_web.dto.PageRequestDTO;
import com.rental_web.dto.PageResponseDTO;
import com.rental_web.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfRenboard(Long rennum, PageRequestDTO pageRequestDTO);

}
