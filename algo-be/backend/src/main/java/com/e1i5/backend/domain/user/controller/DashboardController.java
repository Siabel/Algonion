package com.e1i5.backend.domain.user.controller;

import com.e1i5.backend.domain.user.dto.response.AccCountGraphResponse;
import com.e1i5.backend.domain.user.dto.response.AlgoScoreCountResponse;
import com.e1i5.backend.domain.user.dto.response.DashBoardProblemListResponse;
import com.e1i5.backend.domain.user.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 난이도 기준 top100 문제 가져오기
     * @param nickname
     * @return 난이도 상위 100문제 리스트 반환
     */
    @Operation(summary = "난이도 기준 top100 문제 가져오기")
    @GetMapping("/top100")
    public ResponseEntity<List<DashBoardProblemListResponse>> getTop100ProblemsByNickname(@RequestParam("nickname") String nickname) {
        List<DashBoardProblemListResponse> problemsList = dashboardService.getTop100ProblemsByNickname(nickname);
        return new ResponseEntity<>(problemsList, HttpStatus.OK);
    }

    /**
     * 난이도별 갯수 가져오기
     * @param nickname
     * @return 난이도 별 갯수 리스트 반환
     */
    @Operation(summary = "난이도별 갯수 가져오기")
    @GetMapping("/level-graph")
    public ResponseEntity<int[]> getAlgoScoreCountsByNickname(@RequestParam("nickname") String nickname) {
        int[] algoScoreCounts = dashboardService.getAlgoScoreCountsByNickname(nickname);
        return new ResponseEntity<>(algoScoreCounts, HttpStatus.OK);
    }

    /**
     * 카테고리별 갯수 가져오기
     * @param nickname
     * @return 카테고리 별 갯수 리스트 반환
     */
    @GetMapping("/category-graph")
    public ResponseEntity<int[]> getCategoryCountsByNickname(@RequestParam("nickname") String nickname) {
        int[] categoryCounts = dashboardService.getCategoryCountsByNickname(nickname);
        return new ResponseEntity<>(categoryCounts, HttpStatus.OK);
    }

    /**
     * 누적 개수 합 그래프
     * @return
     */
    @Operation(summary = "누적 개수 합 그래프")
    @GetMapping("/problem-stack")
    public ResponseEntity<AccCountGraphResponse> getProblemCountSum(@RequestParam("nickname") String nickname) {
        AccCountGraphResponse graphData = dashboardService.makeAccumulatedNumGraph(nickname);
        System.out.println("그래프 : " + graphData);
        return new ResponseEntity<>(graphData, HttpStatus.OK);
    }

    /**
     * 누적 Score 합 그래프
     * @return 누적 Score 리스트 반환
     */
    @GetMapping("/point-stack")
    public ResponseEntity<?> getProblemScoreSum(@RequestParam("nickname") String nickname) {
        AccCountGraphResponse graphData = dashboardService.makeAccumulatedAlgoScoreGraph(nickname);
        return new ResponseEntity<>(graphData, HttpStatus.OK);
    }

}

