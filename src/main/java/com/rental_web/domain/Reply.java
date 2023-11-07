package com.rental_web.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;


@Entity
@Table(name = "Reply", indexes = {@Index(name = "idx_reply_renboard_rennum",
                                        columnList = "renboard_rennum")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "renboard")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Renboard renboard;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }

}

