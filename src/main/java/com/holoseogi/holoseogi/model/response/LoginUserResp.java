package com.holoseogi.holoseogi.model.response;

import com.holoseogi.holoseogi.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginUserResp {
    private Long id;
    private String email;
    private String name;
    private String img;
    private String phone;
    private String gender;
    private String region;
    private Integer age;

    public static LoginUserResp getLoginUserResp(User user) {
        return LoginUserResp.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .phone(user.getPhone())
                .gender(user.getGender().getLabel())
                .region(user.getRegion().getLabel())
                .age(user.getAge())
                .build();
    }

}
