package com.saehan.shop.web.dto;

import com.saehan.shop.domain.item.Item;
import com.saehan.shop.domain.user.Role;
import com.saehan.shop.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.persistence.*;

@Getter
@Setter
public class UserFormDto {


    private Long id;

    private String name;

    private String email;

    private String picture;

    private Role role;


    private static ModelMapper modelMapper = new ModelMapper();

    public User createUser(){
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper.map(this, User.class);
    }

    public static UserFormDto of(User user){
        modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(user, UserFormDto.class);
    }
}
