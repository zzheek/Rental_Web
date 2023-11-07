package com.rental_web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RenboardListReplyCountDTO {

    private Long rennum;
    private String rentitle;
    private String renwriter;
    private LocalDateTime regDate;

    private Long replyCount;

}
