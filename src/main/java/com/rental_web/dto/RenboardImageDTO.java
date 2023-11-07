package com.rental_web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenboardImageDTO {

    public String uuid;

    public String fileName;

    private int ord;
}
