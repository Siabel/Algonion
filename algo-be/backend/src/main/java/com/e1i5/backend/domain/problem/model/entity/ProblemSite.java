package com.e1i5.backend.domain.problem.model.entity;

public enum ProblemSite {
    PROGRAMMERS("Programmers"), BAEKJOON("Baekjoon"), SWEA("Swea");

    private final String problemSite;

    ProblemSite(String problemSite) {
        this.problemSite = problemSite;
    }

    public String getProblemSite() {
        return problemSite;
    }
}
