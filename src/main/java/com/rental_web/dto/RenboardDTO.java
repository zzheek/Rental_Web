package com.rental_web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenboardDTO {

    private Long rennum;

    @NotEmpty
    private String rentitle;

    @NotEmpty
    private String rencontent;

    @NotEmpty
    private String renwriter;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<String> fileNames;

}
