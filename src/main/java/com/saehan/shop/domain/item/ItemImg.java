package com.saehan.shop.domain.item;

import com.saehan.shop.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "item_img")
@NoArgsConstructor
public class ItemImg extends BaseTimeEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    @Builder
    public ItemImg(String imgName, String oriImgName, String imgUrl, String repImgYn){
        this.imgUrl = imgUrl;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.repImgYn = repImgYn;
    }
}
