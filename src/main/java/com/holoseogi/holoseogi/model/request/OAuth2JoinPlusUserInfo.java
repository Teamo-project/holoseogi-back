package com.holoseogi.holoseogi.model.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class OAuth2JoinPlusUserInfo {
    @NotNull
    private Long userId;
    private String phone;
    private String region;
    private Integer age;
    private String gender;
    private String role;
}
