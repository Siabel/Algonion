package com.e1i5.backend.domain.problem.response;

import lombok.*;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {
    private String siteName;
    private String problemNum;
    private String problemTitle;
    private String problemLevel;
    private String url;

    @Builder
    public ProblemResponse(String siteName, String problemNum, String problemTitle, String problemLevel, String url) {
        this.siteName = siteName;
        this.problemNum = problemNum;
        this.problemTitle = problemTitle;
        this.problemLevel = problemLevel;
        this.url = url;
    }

}