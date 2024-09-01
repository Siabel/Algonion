package com.e1i5.backend.domain.problem.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotionRequest {
    private String apiKey;
    private String dbId;
    private int solvedProblemId;

    @Builder
    public NotionRequest(String apiKey, String dbId, int solvedProblemId) {
        this.apiKey = apiKey;
        this.dbId = dbId;
        this.solvedProblemId = solvedProblemId;
    }
}
