package com.e1i5.backend.domain.user.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendListResponse {
    String nickname;
    String tier;
    int userScore;

    public FriendListResponse(String nickname, String tier, Integer userScore) {
        this.nickname = nickname;
        this.tier = tier;
        this.userScore = userScore;
    }
}
