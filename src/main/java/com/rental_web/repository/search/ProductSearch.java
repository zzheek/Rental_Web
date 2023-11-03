package com.rental_web.repository.search;

import com.rental_web.domain.Product;
import com.rental_web.dto.product.ProductListAllDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<Product> search1(Pageable pageable);

    Page<Product> searchAll(String[] types, String keyword, Pageable pageable);

}

