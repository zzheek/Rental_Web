package com.rental_web.service;

import com.rental_web.domain.Product;
import com.rental_web.dto.PageRequestDTO;
import com.rental_web.dto.PageResponseDTO;
import com.rental_web.dto.product.ProductDTO;
import com.rental_web.dto.product.ProductListAllDTO;
import com.rental_web.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
        Long productnum = productRepository.save(product).getProductnum();
        return productnum;
    }

    @Override
    public ProductDTO readOne(Long productnum) {
        Optional<Product> result = productRepository.findByIdWithImages(productnum);

        Product product = result.orElseThrow();

        ProductDTO productDTO = entityToDTO(product);

        return productDTO;
    }

    @Override
    public void modify(ProductDTO productDTO) {
        Optional<Product> result = productRepository.findById(productDTO.getProductnum());

        Product product = result.orElseThrow();

        product.change(productDTO.getProductname(), productDTO.getProductfeat(), productDTO.getProductprice());

        //첨부파일의 처리
        product.clearImages();

        if(productDTO.getFileNames() != null){
            for (String fileName : productDTO.getFileNames()) {
                String[] arr = fileName.split("_");
                product.addImage(arr[0], arr[1]);
            }
        }

        productRepository.save(product);

    }

    @Override
    public void remove(Long productnum) {
        productRepository.deleteById(productnum);

    }

    @Override
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("productnum");

        Page<Product> result = productRepository.searchAll(types, keyword, pageable);

        List<ProductDTO> dtoList = result.getContent().stream()
                .map(product -> modelMapper.map(product,ProductDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<ProductDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

}
