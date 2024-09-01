package com.e1i5.backend.domain.user.entity;

import com.e1i5.backend.domain.problem.model.entity.SolvedProblem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private int userId;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "tier")
    private int tier;

    @Column(name = "user_score")
    private int userScore;

    @Column(name = "today_streak_count")
    private int todayStreakCount; //오늘로부터 스트릭 계수

    @Column(name = "last_solved_date")
    private LocalDate lastSolvedDate; //마지막으로 문제 푼 날짜

    @Column(name = "reg_date")
    private LocalDate regDate;

    @Column(name = "status")
    private boolean status; // 탈퇴 여부

    @Column(name = "del_date")
    private LocalDate delDate;

    @OneToMany(mappedBy = "user")
    private List<SolvedProblem> solvedProblems; // 사용자별 푼 문제수를 위함

    @Builder
    public User(int userId, String nickname, String email, LocalDate regDate, int tier, int userScore) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.regDate = regDate;
        this.tier = 0;
        this.userScore = userScore;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void withdrawUser(boolean status, LocalDate delDate) {
        this.status = status;
        this.delDate = delDate;
    }

    public User updateStreakCountAndlastSolvedDate(int count, LocalDate today) {
        this.todayStreakCount = count;
        this.lastSolvedDate = today;
        return this;
    }

}
