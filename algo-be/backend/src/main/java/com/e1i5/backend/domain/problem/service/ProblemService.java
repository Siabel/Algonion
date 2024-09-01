package com.e1i5.backend.domain.problem.service;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.request.SolvedProblemRequest;
import com.e1i5.backend.domain.problem.response.SolvedProblemDetailResponse;
import com.e1i5.backend.domain.user.entity.User;

import java.util.List;

public interface ProblemService {
    int getOldAlgoScore(String problemNum, String siteName);

    Problem saveOrUpdateProblem(Problem problem, String siteName, List<String> problemCategories);
    void saveBojProblemAndClassification(int problemNum);

}
