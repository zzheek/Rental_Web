package com.rental_web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AscalDTO {
    private Long ascalnum;

    @NotNull
    private String ascalwriter;

    @NotNull
    private String ascaltime;

    private String ascalText;
}
