package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.User;
import lombok.Getter;



@Getter
public class UserInfoResp {
    private Long userId;
    private String region;
    private String gender;
    private Integer age;
    private String phone;
    private String password;
    private String name;

    public UserInfoResp(User user) {
        this.userId = user.getId();
        this.region = user.getRegion().getLabel();
        this.gender = user.getGender().getLabel();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.name = user.getName();
    }
}
