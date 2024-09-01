package com.e1i5.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NicknameResponse {
    String nickname;

    @Builder
    public NicknameResponse(String nickname) {
        this.nickname = nickname;
    }
}
