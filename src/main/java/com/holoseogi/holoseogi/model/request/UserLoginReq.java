package com.holoseogi.holoseogi.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginReq {

    private String email;
    private String password;
}
