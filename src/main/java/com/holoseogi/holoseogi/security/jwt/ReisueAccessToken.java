package com.holoseogi.holoseogi.security.jwt;

import lombok.Getter;

@Getter
public class ReisueAccessToken {
    private final String accessToken;

    public ReisueAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
