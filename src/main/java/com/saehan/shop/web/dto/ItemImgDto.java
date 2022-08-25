package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.ItemImg;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
public class ItemImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;
    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
