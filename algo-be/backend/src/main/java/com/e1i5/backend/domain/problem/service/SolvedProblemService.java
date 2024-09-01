package com.e1i5.backend.domain.problem.service;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.request.SolvedProblemRequest;
import com.e1i5.backend.domain.problem.response.SolvedProblemDetailResponse;
import com.e1i5.backend.domain.problem.response.SolvedProblemListResponse;

import java.util.List;

public interface SolvedProblemService {
    void saveBOJSolvedProblemAndProblem(SolvedProblemRequest solvedProblemReq, String siteName, int userId);
    void saveNotBOJSolvedProblemAndProblem(SolvedProblemRequest solvedProblemReq, String siteName, int userId);
    SolvedProblemRequest getUpdatedSolvedProblemRequest(int problemNum);
    void saveSolvedProblem(SolvedProblemRequest solvedProblemReq, int userId, Problem problem, int oldAlgoScore);
    SolvedProblemDetailResponse updateSolvedProblem(int userId, int solvedProblemId, String memo);
    SolvedProblemDetailResponse updateVisibility(int userId, int solvedProblemId);
    List<SolvedProblemListResponse> getSolvedProblemListByUser(String nickname, int pageNum);
    SolvedProblemDetailResponse getSolvedProblemDetail(int solvedProblemId);
    int countUserSolvedProblem(String nickname);
}
