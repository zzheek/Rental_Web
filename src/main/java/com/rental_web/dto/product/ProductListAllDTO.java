package com.rental_web.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListAllDTO {

    private Long productnum;

    private String productname;

    private String productimag;

    private String productfeat;

    private Long productprice;

    //private List<String> fileNames;

    private List<ProductImageDTO> productImages;

}
