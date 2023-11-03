package com.rental_web.repository;

import com.rental_web.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.rental_web.repository.search.ProductSearch;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select p from Product p where p.productnum =:productnum")
    Optional<Product> findByIdWithImages(Long productnum);

}
