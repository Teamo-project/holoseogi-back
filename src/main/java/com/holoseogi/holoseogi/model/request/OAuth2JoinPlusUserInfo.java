package com.holoseogi.holoseogi.model.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class OAuth2JoinPlusUserInfo {
    @NotNull
    private Long userId;
    private Integer phone;
    private String area;
    private String age;
}
