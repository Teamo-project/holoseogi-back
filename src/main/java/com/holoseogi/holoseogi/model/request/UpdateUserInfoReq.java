package com.holoseogi.holoseogi.model.request;


import com.holoseogi.holoseogi.model.entity.User;
import com.holoseogi.holoseogi.type.UserGender;
import com.holoseogi.holoseogi.type.UserRegion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoReq {
    private Long userId;
    private String region;
    private Integer age;
    private String phone;
    private String password;
    private String name;
}
