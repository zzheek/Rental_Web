package com.rental_web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class ProductImage implements Comparable<ProductImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Product product;

    @Override
    public int compareTo(ProductImage other){
        return this.ord - other.ord;
    }

    public void changeProduct(Product product){
        this.product = product;
    }


}
