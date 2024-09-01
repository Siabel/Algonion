package com.e1i5.backend.domain.problem.request;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.model.entity.SolvedProblem;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedProblemRequest {
    private String submissionId; //제출번호
    private String problemNum; //문제번호
    private String problemTitle; //문제 제목
    private String problemLevel;
    private String memory;
    private String runtime; //코드 실행 시간
    private String language; 
    private String submissionCode; //내가 푼 코드
    private String memo;
    private String codeLength; //코드 길이
    private String submissionTime; //제출한 시간
    private String url;
    private List<String> problemCategory; // 문제 카테고리 리스트

    public SolvedProblem toSolvedProblemEntity() {
        return SolvedProblem.builder()
                .submissionId(submissionId)
                .language(language)
                .submissionCode(submissionCode)
                .memo(memo)
                .runtime(runtime)
                .codeLength(codeLength)
                .memory(memory)
                .submissionTime(LocalDate.now().toString())
                .visible(true)
                .build();
    }

    public Problem toProblemEntity() {
//        int algoScore = AlgoScoreUtil.getBojScore(Integer.parseInt(problemLevel));
        return Problem.builder()
                .problemNum(problemNum)
                .problemTitle(problemTitle)
                .problemLevel(problemLevel)
//                .algoScore(algoScore)
                .url(url)
                .build();
    }


    @Builder
    public SolvedProblemRequest(String submissionId,
                                String problemNum,
                                String problemTitle,
                                String problemLevel,
                                String memory,
                                String runtime,
                                String language,
                                String submissionCode,
                                String memo,
                                String codeLength,
                                String submissionTime,
                                String url,
                                List<String> problemCategories) {
        this.submissionId = submissionId;
        this.problemNum = problemNum;
        this.problemTitle = problemTitle;
        this.problemLevel = problemLevel;
        this.memory = memory;
        this.runtime = runtime;
        this.language = language;
        this.submissionCode = submissionCode;
        this.memo = memo;
        this.codeLength = codeLength;
        this.submissionTime = submissionTime;
        this.url = url;
        this.problemCategory = problemCategories;
    }

    @Builder
    public SolvedProblemRequest(String problemTitle, String problemLevel, List<String> problemCategory) {
        this.problemTitle = problemTitle;
        this.problemLevel = problemLevel;
        this.problemCategory = problemCategory;
    }

    public void updateProblemInfo(String problemTitle, String problemLevel, List<String> problemCategory) {
        this.problemTitle = problemTitle;
        this.problemLevel = problemLevel;
        this.problemCategory = problemCategory;
//        return this;
    }

}
