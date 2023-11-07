package com.rental_web.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.rental_web.domain.QRenboard;
import com.rental_web.domain.QReply;
import com.rental_web.domain.Renboard;
import com.rental_web.dto.RenboardImageDTO;
import com.rental_web.dto.RenboardListAllDTO;
import com.rental_web.dto.RenboardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class RenboardSearchImpl extends QuerydslRepositorySupport implements RenboardSearch {

    public RenboardSearchImpl() {
        super(Renboard.class);
    }

    @Override
    public Page<Renboard> search2(Pageable pageable) {

        QRenboard renboard = QRenboard.renboard;

        JPQLQuery<Renboard> query = from(renboard);

        query.where(renboard.rentitle.contains("1"));

        this.getQuerydsl().applyPagination(pageable,query);

        List<Renboard> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    public Page<Renboard> searchAll(String[] types, String keyword, Pageable pageable) {

        QRenboard renboard = QRenboard.renboard;
        JPQLQuery<Renboard> query = from(renboard);

        if((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type: types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(renboard.rentitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(renboard.rencontent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(renboard.renwriter.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(renboard.rennum.gt(0L)); // rennum > 0

        this.getQuerydsl().applyPagination(pageable,query);

        List<Renboard> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);

    }

    @Override
    public Page<RenboardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QRenboard renboard = QRenboard.renboard;
        QReply reply = QReply.reply;

        JPQLQuery<Renboard> query = from(renboard);
        query.leftJoin(reply).on(reply.renboard.eq(renboard));

        query.groupBy(renboard);

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(renboard.rentitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(renboard.rencontent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(renboard.renwriter.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }

        //bno > 0
        query.where(renboard.rennum.gt(0L));


        JPQLQuery<RenboardListReplyCountDTO> dtoQuery = query.select(Projections.
                bean(RenboardListReplyCountDTO.class,
                        renboard.rennum,
                        renboard.rentitle,
                        renboard.renwriter,
                        renboard.regDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable,dtoQuery);

        List<RenboardListReplyCountDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);


    }

    @Override
    public Page<RenboardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
        QRenboard renboard = QRenboard.renboard;
        QReply reply = QReply.reply;

        JPQLQuery<Renboard> renboardJPQLQuery = from(renboard);
        renboardJPQLQuery.leftJoin(reply).on(reply.renboard.eq(renboard)); //left join

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(renboard.rentitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(renboard.rencontent.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(renboard.renwriter.contains(keyword));
                        break;
                }
            }//end for
            renboardJPQLQuery.where(booleanBuilder);
        }

        renboardJPQLQuery.groupBy(renboard);

        getQuerydsl().applyPagination(pageable, renboardJPQLQuery); //paging



        JPQLQuery<Tuple> tupleJPQLQuery = renboardJPQLQuery.select(renboard, reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<RenboardListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Renboard renboard1 = (Renboard) tuple.get(renboard);
            long replyCount = tuple.get(1,Long.class);

            RenboardListAllDTO dto = RenboardListAllDTO.builder()
                    .rennum(renboard1.getRennum())
                    .rentitle(renboard1.getRentitle())
                    .renwriter(renboard1.getRenwriter())
                    .regDate(renboard1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            //RenboardImage를 RenboardImageDTO 처리할 부분
            List<RenboardImageDTO> imageDTOS = renboard1.getImageSet().stream().sorted()
                    .map(renboardImage -> RenboardImageDTO.builder()
                            .uuid(renboardImage.getUuid())
                            .fileName(renboardImage.getFileName())
                            .ord(renboardImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());

            dto.setRenboardImages(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

        long totalCount = renboardJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}
