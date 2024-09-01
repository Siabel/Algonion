package com.e1i5.backend.domain.problem.response;

import com.e1i5.backend.domain.problem.model.entity.SolvedProblem;
import lombok.*;

import java.sql.Clob;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedProblemDetailResponse {
    private String siteName;
    private String submissionId; //제출번호
    private String problemNum; //문제번호
    private String problemTitle; //문제 제목
    private String problemLevel;
    private String memory;
    private String runtime; //코드 실행 시간
    private String language;
    private String submissionCode; //내가 푼 코드
    private String codeLength; //코드 길이
    private String submissionTime;
    private String url;
    private String memo;
    private boolean visible;

    @Builder
    public SolvedProblemDetailResponse(SolvedProblem solvedProblem) {
        this.siteName = solvedProblem.getProblem().getSiteName();
        this.submissionId = solvedProblem.getSubmissionId();
        this.problemNum = solvedProblem.getProblem().getProblemNum();
        this.problemTitle = solvedProblem.getProblem().getProblemTitle();
        this.problemLevel = solvedProblem.getProblem().getProblemLevel();
        this.memory = solvedProblem.getMemory();
        this.runtime = solvedProblem.getRuntime();
        this.language = solvedProblem.getLanguage();
        this.submissionCode = solvedProblem.getSubmissionCode();
        this.codeLength = solvedProblem.getCodeLength();
        this.submissionTime = solvedProblem.getSubmissionTime();
        this.url = solvedProblem.getProblem().getUrl();
        this.memo = solvedProblem.getMemo();
        this.visible = solvedProblem.isVisible();
    }


}
