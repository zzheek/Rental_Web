package com.rental_web.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productnum;

    @Column(length = 1000, nullable = false)
    private String productname;

    @Column(length = 1000, nullable = true)
    private String productimag;

    @Column(length = 1000, nullable = true)
    private String productfeat;

    private Long productprice;

    public void change(String productname, String productfeat, String productimag, Long productprice){
        this.productname = productname;
        this.productfeat = productfeat;
        this.productimag = productimag;
        this.productprice = productprice;
    }

    @OneToMany(mappedBy = "product",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<ProductImage> imageSet = new HashSet<>();

    public void  addImage(String uuid, String fileName){
        ProductImage productImage = ProductImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .product(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(productImage);
    }

    public void clearImages(){

        imageSet.forEach(productImage -> productImage.changeProduct(null));

        this.imageSet.clear();
    }


}
