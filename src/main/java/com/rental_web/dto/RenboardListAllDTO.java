package com.rental_web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RenboardListAllDTO {

    private Long rennum;

    private String rentitle;

    private String renwriter;

    private LocalDateTime regDate;

    private Long replyCount;

    private List<RenboardImageDTO> renboardImages;

}
