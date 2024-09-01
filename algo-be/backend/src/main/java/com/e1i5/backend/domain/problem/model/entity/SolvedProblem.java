package com.e1i5.backend.domain.problem.model.entity;

import com.e1i5.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Clob;
import java.time.LocalDateTime;

@Entity
@Table(name = "solved_problem")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solved_problem_id")
    private int solvedProblemId;

    @Column(name = "submission_id")
    private String submissionId; //제출 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "language")
    private String language;

    //@Lob 어노테이션은 DB에서 varchar보다 더 많은 데이터를 담으려고 사용한 거임 코드나 메모는 길어질 수 있으니까
    @Column(name = "submission_code", columnDefinition = "TEXT")
    @Lob
    private String submissionCode;

    @Column(name = "memo", columnDefinition = "TEXT")
    @Lob
    private String memo;

    @Column(name = "memory")
    private String memory;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "code_length")
    private String codeLength;

    @Column(name = "submission_time")
//    private LocalDateTime submissionTime; //문제 푼 날짜,시간
    private String submissionTime;

    @Column(name = "visible")
    private boolean visible;

    @Builder
    public SolvedProblem(String submissionId, User user, Problem problem, String language, String submissionCode, String memo, String memory, String runtime, String codeLength, String submissionTime, boolean visible) {
        this.submissionId = submissionId;
        this.user = user;
        this.problem = problem;
        this.language = language;
        this.submissionCode = submissionCode;
        this.memo = memo;
        this.memory = memory;
        this.runtime = runtime;
        this.codeLength = codeLength;
        this.submissionTime = submissionTime;
        this.visible = visible;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateVisible(boolean visible) {
        this.visible = visible;
    }

    public void updateUserAndProblem(User user, Problem problem) {
        this.user = user;
        this.problem = problem;
    }
}
