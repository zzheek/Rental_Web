package com.rental_web.service;

import com.rental_web.domain.Product;
import com.rental_web.domain.ProductImage;
import com.rental_web.dto.PageRequestDTO;
import com.rental_web.dto.PageResponseDTO;
import com.rental_web.dto.product.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {

    Long register(ProductDTO productDTO);

    ProductDTO readOne(Long productnum);

    void modify(ProductDTO productDTO);

    void remove(Long productnum);

    PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO);

    default Product dtoToEntity(ProductDTO productDTO){
        Product product = Product.builder()
                .productnum(productDTO.getProductnum())
                .productname(productDTO.getProductname())
                .productfeat(productDTO.getProductfeat())
                .productprice(productDTO.getProductprice())
                .build();

        if(productDTO.getFileNames() != null){
            productDTO.getFileNames().forEach(fileName ->{
                String[] arr =fileName.split("_");
                product.addImage(arr[0],arr[1]);
            });
        }
        return product;
    }

    default ProductDTO entityToDTO(Product product){
        ProductDTO productDTO = ProductDTO.builder()
                .productnum(product.getProductnum())
                .productname(product.getProductname())
                .productfeat(product.getProductfeat())
                .productprice(product.getProductprice())
                .build();
        List<String> fileNames =
                product.getImageSet().stream().sorted().map(productImage ->
                        productImage.getUuid()+"_"+productImage.getFileName()).collect(Collectors.toList());

        productDTO.setFileNames(fileNames);

        return productDTO;
    }
}

