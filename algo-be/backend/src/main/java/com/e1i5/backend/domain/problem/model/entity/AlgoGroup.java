package com.e1i5.backend.domain.problem.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "algo_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "classification")
    private String classification;

    @Builder
    public AlgoGroup(Problem problem, String classification) {
        this.problem = problem;
        this.classification = classification;
    }
}
