package com.e1i5.backend.domain.problem.repository;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.response.ProblemInterfaceResponse;
import com.e1i5.backend.domain.problem.response.ProblemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    Optional<Problem> findByProblemNumAndSiteName(String problemNum, String siteName);
    @Query("SELECT p.algoScore FROM Problem p WHERE p.problemId = :problemId")
    Integer findAlgoScoreByProblemId(@Param("problemId") int problemId);
    @Query("SELECT new com.e1i5.backend.domain.problem.response.ProblemResponse(" +
            "p.siteName, p.problemNum, p.problemTitle, p.problemLevel, p.url) " +
            "FROM Problem p " +
            "WHERE p.problemId NOT IN " +
            "(SELECT sp.problem.problemId " +
            "FROM SolvedProblem sp " +
            "WHERE sp.user.userId = :userId)")
    List<ProblemResponse> findUnsolvedProblemsByUserId(@Param("userId") int userId);

    @Query("SELECT p.problemId  " +
            "FROM Problem p " +
            "WHERE p.problemId NOT IN " +
            "(SELECT sp.problem.problemId " +
            "FROM SolvedProblem sp " +
            "WHERE sp.user.userId = :userId)")
    int[] findUnsolvedProblemsIdsByUserId(@Param("userId") int userId);

    @Query("SELECT p.siteName AS siteName, p.url AS url, p.problemNum AS problemNum, " +
            "p.problemTitle AS problemTitle, p.problemLevel AS problemLevel " +
            "FROM Problem p WHERE p.problemId IN :problemIds")
    List<ProblemInterfaceResponse> findByProblemIdIn(List<Integer> problemIds);
}
