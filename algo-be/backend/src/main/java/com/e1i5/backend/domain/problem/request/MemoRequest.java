package com.e1i5.backend.domain.problem.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoRequest {
    String memo;

    @Builder
    public MemoRequest(String memo) {
        this.memo = memo;
    }
}
