package com.holoseogi.holoseogi.security.jwt;

import lombok.Getter;

@Getter
public class ReissueAccessToken {
    private final String accessToken;

    public ReissueAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
