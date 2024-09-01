package com.e1i5.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdResponse {
    int userId;

    @Builder
    public UserIdResponse(int userId) {
        this.userId = userId;
    }
}
