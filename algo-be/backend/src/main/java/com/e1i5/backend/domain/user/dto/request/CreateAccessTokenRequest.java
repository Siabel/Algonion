package com.e1i5.backend.domain.user.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateAccessTokenRequest {
    private String refreshToken;

    @Builder
    public CreateAccessTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}