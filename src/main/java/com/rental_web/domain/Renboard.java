package com.rental_web.domain;

import com.rental_web.domain.BaseEntity;
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
@ToString(exclude = "imageSet")
public class Renboard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rennum;

    @Column(length = 500, nullable = false)
    private String rentitle;

    @Column(length = 5000, nullable = false)
    private String rencontent;

    @Column(length = 100, nullable = false)
    private String renwriter;

    public void change(String rentitle, String rencontent) {
        this.rentitle = rentitle;
        this.rencontent = rencontent;

    }

    @OneToMany(mappedBy = "renboard",
            cascade = {CascadeType.ALL},    // renboard 객체 상태 변화시 renboardimage 객체 같이 변경
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<RenboardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){

        RenboardImage renboardImage = RenboardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .renboard(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(renboardImage);
    }

    public void clearImages() {

        imageSet.forEach(renboardImage -> renboardImage.changeRenboard(null));

        this.imageSet.clear();
    }

}
