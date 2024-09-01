package com.e1i5.backend.global.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenValidResultResponse {
    private boolean isValid;
    private String errMsg;
    private int statusCode;

    @Builder
    public TokenValidResultResponse(boolean isValid, String errMsg, int statusCode) {
        this.isValid = isValid;
        this.errMsg = errMsg;
        this.statusCode = statusCode;
    }

    public boolean isValid() {
        return isValid;
    }
}

