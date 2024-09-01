package com.e1i5.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateAccessTokenResponse {
    private String accessToken;

    @Builder
    public CreateAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}