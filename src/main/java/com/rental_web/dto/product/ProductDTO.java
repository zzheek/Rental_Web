package com.rental_web.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {


    @NotEmpty
    private Long productnum;

    @NotEmpty
    private String productname;

    private String productimag;

    private String productfeat;

    private Long productprice;

    private List<String> fileNames;

}
