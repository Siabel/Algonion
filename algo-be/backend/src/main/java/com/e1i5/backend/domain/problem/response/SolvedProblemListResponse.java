package com.e1i5.backend.domain.problem.response;

import com.e1i5.backend.domain.problem.model.entity.SolvedProblem;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedProblemListResponse {
    private int solvedId; // 사용자가 푼 문제 인덱스
    private String siteName;
    private String problemNum; //문제번호
    private String problemTitle; //문제 제목
    private String problemLevel;
    private String language;
    private String submissionTime; //제출한 시간
    private String url;
    private List<String> classification;
    private String strClassification;

    @Builder
    public SolvedProblemListResponse(SolvedProblem solvedProblem,List<String> classification, String strClassification) {
        this.solvedId = solvedProblem.getSolvedProblemId();
        this.siteName = solvedProblem.getProblem().getSiteName();
        this.problemNum = solvedProblem.getProblem().getProblemNum();
        this.problemTitle = solvedProblem.getProblem().getProblemTitle();
        this.problemLevel = solvedProblem.getProblem().getProblemLevel();
        this.language = solvedProblem.getLanguage();
        this.submissionTime = solvedProblem.getSubmissionTime();
        this.url = solvedProblem.getProblem().getUrl();
        this.classification = classification;
        this.strClassification = strClassification;
    }
}
