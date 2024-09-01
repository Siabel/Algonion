package com.e1i5.backend.domain.user.repository;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardRepository extends JpaRepository<Problem, Integer> {
    @Query("SELECT DISTINCT p " +
            "FROM SolvedProblem sp " +
            "JOIN sp.problem p " +
            "WHERE sp.user.userId = :userId " +
            "ORDER BY p.algoScore DESC " +
            "LIMIT 100")
    List<Problem> findProblemsByNicknameOrderedByAlgoScore(@Param("userId") int userId);

    @Query("SELECT sp.problem.problemId, ag.classification " +
            "FROM SolvedProblem sp " +
            "JOIN sp.problem p " +
            "JOIN p.algoGroup ag " +
            "WHERE sp.user.userId = :userId " +
            "GROUP BY sp.problem.problemId, ag.classification")
    List<Object[]> findAlgoGroupsByUserId(@Param("userId") int userId);

    @Query("SELECT sp.problem.algoScore, COUNT(DISTINCT sp.problem.problemId) " +
            "FROM SolvedProblem sp " +
            "WHERE sp.user.userId = :userId " +
            "GROUP BY sp.problem.algoScore " +
            "ORDER BY sp.problem.algoScore ASC")
    List<Object[]> findAlgoScoreCountsByUserId(@Param("userId") int userId);

//    @Query("SELECT MIN(sp.submissionTime), sp.problem.algoScore, sp.problem.problemId " +
//            "FROM SolvedProblem sp " +
//            "WHERE sp.user.userId = :userId " +
//            "GROUP BY sp.problem.problemId " +
//            "ORDER BY MIN(sp.submissionTime)")
//    List<Object[]> findFirstSubmissionDateAndAccumulatedAlgoScoreByUserId(@Param("userId") int userId);

    @Query("SELECT MIN(submissionTime), SUM(algoScore) " +
            "FROM (" +
            "    SELECT MIN(sp.submissionTime) AS submissionTime, sp.problem.algoScore AS algoScore " +
            "    FROM SolvedProblem sp " +
            "    WHERE sp.user.userId = :userId " +
            "    GROUP BY sp.problem.problemId)" +
            "GROUP BY submissionTime " +
            "ORDER BY MIN(submissionTime)")
    List<Object[]> findFirstSubmissionDateAndAccumulatedAlgoScoreByUserId(@Param("userId") int userId);

}
