package com.e1i5.backend.domain.user.dto.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameRequest {
    String nickname;

    @Builder
    public NicknameRequest(String nickname) {
        this.nickname = nickname;
    }
}
