package com.e1i5.backend.domain.problem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private int problemId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "num")
    private String problemNum; //문제 번호

    @Column(name = "title")
    private String problemTitle; //문제 이름

    @Column(name = "level")
    private String problemLevel;

    @Column(name = "algo_score")
    private int algoScore; // 우리 사이트 점수

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "problem")
    private List<AlgoGroup> algoGroup;

    @Builder
    public Problem(String siteName, String problemNum, String problemTitle, String problemLevel, int algoScore, String url) {
        this.siteName = siteName;
        this.problemNum = problemNum;
        this.problemTitle = problemTitle;
        this.problemLevel = problemLevel;
        this.algoScore = algoScore;
        this.url = url;
    }

    public void updateSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Problem updateLevel(String level, int algoScore) { //TODO 레벨과 점수를 동시에 세팅하므로 메서드 이름을 updateLevelAndScore로 변경
        this.problemLevel = level;
        this.algoScore = algoScore;
        return this;
    }

}
