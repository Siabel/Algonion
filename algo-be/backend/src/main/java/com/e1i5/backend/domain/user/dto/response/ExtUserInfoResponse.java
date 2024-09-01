package com.e1i5.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 사용자 프로필 불러오기 - 확장 프로그램
 */
@Getter
public class ExtUserInfoResponse {
    String nickname;
    int tier;
    Map<String, Long> streak;

    @Builder
    public ExtUserInfoResponse(String nickname, int tier, Map<String, Long> streak) {
        this.nickname = nickname;
        this.tier = tier;
        this.streak = streak;
    }
}
