package com.rental_web.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ascal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ascalnum;

    private String ascalwriter;


    private String ascaltime;

    private String ascalText;

    public void changeText(String text) { this.ascalText = text;}

}
