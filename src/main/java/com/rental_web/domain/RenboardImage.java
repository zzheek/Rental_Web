package com.rental_web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "renboard")
public class RenboardImage implements Comparable<RenboardImage> {

    @Id
    private String uuid;        // 파일의 고유값

    private String fileName;    // 파일명

    public int ord;            // 파일 순번

    @ManyToOne
    private Renboard renboard;


    @Override
    public int compareTo(RenboardImage other) {
        return this.ord - other.ord;
    }

    public void changeRenboard(Renboard renboard) {
        this.renboard = renboard;

    }
}
