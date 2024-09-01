package com.e1i5.backend.domain.problem.controller;

import com.e1i5.backend.domain.problem.model.entity.ProblemSite;
import com.e1i5.backend.domain.problem.request.MemoRequest;
import com.e1i5.backend.domain.problem.request.SolvedProblemRequest;
import com.e1i5.backend.domain.problem.response.SolvedProblemDetailResponse;
import com.e1i5.backend.domain.problem.response.SolvedProblemListResponse;
import com.e1i5.backend.domain.problem.service.ProblemService;
import com.e1i5.backend.domain.problem.service.SolvedProblemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/solved-problems")
@Slf4j
public class ProblemController {


    @Autowired
    ProblemService problemService;

    @Autowired
    SolvedProblemService solvedProblemService;

    @Operation(summary = "백준 문제 저장")
    @PostMapping("/baekjoon")
    public ResponseEntity<Void> saveBojProblem(
            @RequestBody SolvedProblemRequest problem, Principal principal){
        int userId = Integer.parseInt(principal.getName());

        log.info("ProblemController 백준 SolvedProblemRequest problem: {}", problem.toString());

        solvedProblemService.saveBOJSolvedProblemAndProblem(problem,
                ProblemSite.BAEKJOON.getProblemSite(), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "프로그래머스 문제 저장")
    @PostMapping("/programmers")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> saveProgrammersProblem(
            @RequestBody SolvedProblemRequest problem, Principal principal){
        int userId = Integer.parseInt(principal.getName());

        log.info("ProblemController 프로그래머스 SolvedProblemRequest problem: {}", problem.toString());

        solvedProblemService.saveNotBOJSolvedProblemAndProblem(problem,
                ProblemSite.PROGRAMMERS.getProblemSite(), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "swea 문제 저장")
    @PostMapping("/swea")
    public ResponseEntity<Void> saveSWEA(
            @RequestBody SolvedProblemRequest problem, Principal principal){
//        int userId = 1;
        int userId = Integer.parseInt(principal.getName());

        log.info("ProblemController SWEA SolvedProblemRequest problem: {}", problem.toString());

        solvedProblemService.saveNotBOJSolvedProblemAndProblem(problem, ProblemSite.SWEA.getProblemSite(), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 사용자가 푼 문제 리스드 가져오기
     * @param nickname
     * @return
     * @throws Exception
//     */
    @Operation(summary = "사용자가 푼 문제 리스드 가져오기")
    @GetMapping("/mysolved")
    public ResponseEntity<List<SolvedProblemListResponse>> getSolvedProblemList(
            @RequestParam("nickname") String nickname, @RequestParam("page") int pageNum){

        List<SolvedProblemListResponse> solvedProblemListByUser = solvedProblemService.getSolvedProblemListByUser(nickname, pageNum);

        return new ResponseEntity<>(solvedProblemListByUser, HttpStatus.OK);
    }

    @GetMapping("/mysolved/size")
    public ResponseEntity<Integer> makePaginationSize(@RequestParam String nickname) {
        return new ResponseEntity<>(solvedProblemService.countUserSolvedProblem(nickname), HttpStatus.OK);
    }

    /**
     * 사용자가 푼 문제 상세 정보 조회
     * @return 사용자가 푼 문제 데이터
     */
    @Operation(summary = "사용자가 푼 문제 상세 정보 조회")
    @GetMapping("/mysolved/detail")
    public ResponseEntity<SolvedProblemDetailResponse> getSolvedDetail(
            @RequestParam("solvednum") int solvedProblemId){
        SolvedProblemDetailResponse solvedProblemDetail = solvedProblemService.getSolvedProblemDetail(solvedProblemId);
        return new ResponseEntity<>(solvedProblemDetail, HttpStatus.OK);
    }

    /**
     * 사용자가 푼 문제 메모 수정
     * @param solvedProblemId 수정할 문제 인덱스
     * @param memoRequest 수정할 메모
     * @return 수정한 푼 문제 데이터
     */
    @Operation(summary = "사용자가 푼 문제 메모 수정")
    @PutMapping("/mysolved")
    public ResponseEntity<SolvedProblemDetailResponse> updateSolvedProblemMemo(
            @RequestParam("solvednum") int solvedProblemId,@RequestBody MemoRequest memoRequest, Principal principal){

        int userId = Integer.parseInt(principal.getName());
//        int userId = 1;
        log.info("ProblemController updateSolvedProblemMemo solvedProblemId: {}, memo: {}, userId: {}", solvedProblemId, memoRequest.getMemo(), userId);
        SolvedProblemDetailResponse solvedProblemDetail =
                solvedProblemService.updateSolvedProblem(userId, solvedProblemId, memoRequest.getMemo());
        log.info("ProblemController updateSolvedProblemMemo solvedProblemDetail: {}", solvedProblemDetail.toString());

        return new ResponseEntity<>(solvedProblemDetail, HttpStatus.OK);
    }

    /**
     * 푼 문제 숨기기
     * @param solvedProblemId 수정할 문제 인덱스
     * @return 수정한 푼 문제 데이터
     */
    @Operation(summary = "문제 삭제")
    @PutMapping("/visible")
    public ResponseEntity<SolvedProblemDetailResponse> updateVisibility(
            @RequestParam("solvednum") int solvedProblemId, Principal principal) {

        int userId = Integer.parseInt(principal.getName());
        SolvedProblemDetailResponse solvedProblemDetail = solvedProblemService.updateVisibility(userId, solvedProblemId);

        return new ResponseEntity<>(solvedProblemDetail, HttpStatus.OK);
    }

    /**
     * solved.ac에서 데이터 받아와서 분류하고 저장하는 메서드
     */
    @Operation(summary = "solved.ac에서 데이터 받아와서 분류하고 저장하는 메서드")
    // TODO solved.ac test
    @GetMapping("/solved-ac")
    public void saveSolvedAcApiProblem() {
        for (int i = 1; i <= 30; i++) {
            problemService.saveBojProblemAndClassification(i);
        }
    }

}
