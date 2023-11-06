package com.rental_web.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;


@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_product_productnum", columnList = "product_productnum")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
//@ToString
public class Reply{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }

}

