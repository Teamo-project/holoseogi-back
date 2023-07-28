package com.holoseogi.holoseogi.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResp {

    private final Integer code;
    private final String message;

    @Builder
    public ErrorResp(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
