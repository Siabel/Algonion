package com.e1i5.backend.domain.user.dto.response;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import lombok.*;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DashBoardProblemListResponse {
    private int problemId;
    private String problemNum;
    private String problemTitle;
    private String problemLevel;
    private int algoScore;
    private String siteName;
    private String url;

    @Builder
    public DashBoardProblemListResponse(Problem problem) {
        this.problemId = problem.getProblemId();
        this.problemNum = problem.getProblemNum();
        this.problemTitle = problem.getProblemTitle();
        this.problemLevel = problem.getProblemLevel();
        this.algoScore = problem.getAlgoScore();
        this.siteName = problem.getSiteName();
        this.url = problem.getUrl();
    }
}
