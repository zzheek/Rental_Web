package com.rental_web.repository.search;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.rental_web.domain.Product;
import com.rental_web.domain.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.rental_web.dto.product.ProductImageDTO;
import com.rental_web.dto.product.ProductListAllDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl(){
        super(Product.class);
    }

    @Override
    public Page<Product> search1(Pageable pageable) {

        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(product.productnum.like("11")); // title like ...

        booleanBuilder.or(product.productfeat.contains("11")); // content like ....

        query.where(booleanBuilder);
        query.where(product.productnum.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Product> list = query.fetch();

        long count = query.fetchCount();


        return null;

    }

    @Override
    public Page<Product> searchAll(String[] types, String keyword, Pageable pageable) {

        QProduct product = QProduct.product;
        JPQLQuery<Product> query = from(product);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "n":
                        booleanBuilder.or(product.productname.contains(keyword));
                        break;
                    case "f":
                        booleanBuilder.or(product.productfeat.contains(keyword));
                        break;

                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(product.productnum.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Product> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

}
