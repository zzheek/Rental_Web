package com.rental_web.repository.search;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.rental_web.domain.Product;
import com.rental_web.domain.QProduct;
import com.rental_web.domain.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.rental_web.dto.product.ProductImageDTO;
import com.rental_web.dto.product.ProductListAllDTO;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
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

    @Override
    public Page<ProductListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QProduct product = QProduct.product;
        QReply reply = QReply.reply;

        JPQLQuery<Product> productJPQLQuery = from(product);
        productJPQLQuery.leftJoin(reply).on(reply.product.eq(product)); //left join

        if( (types != null && types.length > 0) && keyword != null ){

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
            productJPQLQuery.where(booleanBuilder);
        }

        productJPQLQuery.groupBy(product);

        getQuerydsl().applyPagination(pageable, productJPQLQuery); //paging



        JPQLQuery<Tuple> tupleJPQLQuery = productJPQLQuery.select(product, reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ProductListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Product product1 = (Product) tuple.get(product);
            //long replyCount = tuple.get(1,Long.class);

            ProductListAllDTO dto = ProductListAllDTO.builder()
                    .productnum(product1.getProductnum())
                    .productname(product1.getProductname())
                    .productfeat(product1.getProductfeat())
                    .productprice(product1.getProductprice())
                    //.replyCount(replyCount)
                    .build();

            //BoardImage를 BoardImageDTO 처리할 부분
            List<ProductImageDTO> imageDTOS = product1.getImageSet().stream().sorted()
                    .map(productImage -> ProductImageDTO.builder()
                            .uuid(productImage.getUuid())
                            .fileName(productImage.getFileName())
                            .ord(productImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());

            dto.setProductImages(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

        long totalCount = productJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList, pageable, totalCount);
    }

}


