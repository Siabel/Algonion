package com.e1i5.backend.domain.problem.service;

import com.e1i5.backend.domain.problem.model.entity.AlgoGroup;
import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.model.entity.ProblemSite;
import com.e1i5.backend.domain.problem.repository.AlgoGroupRepository;
import com.e1i5.backend.domain.problem.repository.ProblemRepository;
//import com.e1i5.backend.domain.problem.repository.SolvedProblemRepository;
//import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.global.util.AlgoScoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemRepository problemRepo;
    @Autowired
    AlgoGroupRepository algoGroupRepo;

    private static final String BOJ_URL = "https://www.acmicpc.net/problem/";
    private static final String SOLVEDAC_URL = "https://solved.ac";

    /**
     * solved.ac에서 문제 데이터 받아오기
     * @param problemNum
     */
    @Override
    public void saveBojProblemAndClassification(int problemNum) {
        String code = "myCode";

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl(SOLVEDAC_URL)
                        .build();

        // api 요청
        Map response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/api/v3/search/problem")
                                        .queryParam("query", "")
                                        .queryParam("page", problemNum)
                                        .build())
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        assert response != null;
//        log.info(response.toString());
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
        List<Problem> problems = new ArrayList<>(); // 받아온 문제들을 넣을 리스트

        for (Map<String, Object> item : items) {
            int problemId = (int) item.get("problemId");
            String titleKo = (String) item.get("titleKo");
            int level = (int) item.get("level");
//            int level = 6;
            List<String> algoGroup = new ArrayList<>();

            List<Map<String, Object>> tags = (List<Map<String, Object>>) item.get("tags");

            for (Map<String, Object> tag : tags) {
                List<Map<String, Object>> displayNames = (List<Map<String, Object>>) tag.get("displayNames");
                algoGroup.add((String) displayNames.get(0).get("name"));
            }

            String url = BOJ_URL + Integer.toString(problemId);

            // 1. 문제 저장 -> 이미 있는 값은 레벨만 변경, 없으면 새로 저장
            Problem problem = Problem.builder()
                    .problemNum(Integer.toString(problemId))
                    .problemTitle(titleKo)
                    .problemLevel(Integer.toString(level))
                    .algoScore(AlgoScoreUtil.getBojScore(level))
                    .url(url).build();
            problem.updateSiteName(ProblemSite.BAEKJOON.getProblemSite());
            Problem newProblem = saveOrUpdateProblem(problem, ProblemSite.BAEKJOON.getProblemSite(), null);

            // 2. 문제의 인덱스를 가지고 문제 알고리즘 분류
            List<AlgoGroup> algoGroupList = new ArrayList<>();
            for (String algo : algoGroup) {
                algoGroupList.add(AlgoGroup.builder()
                        .problem(newProblem)
                        .classification(algo).build());
            }

            algoGroupRepo.saveAll(algoGroupList);
        }

    }

    @Override
    public int getOldAlgoScore(String problemNum, String siteName) {
        Optional<Problem> existingProblem = problemRepo.findByProblemNumAndSiteName(problemNum, siteName);
        return existingProblem.map(Problem::getAlgoScore).orElse(0);
    }

    @Override
    public Problem saveOrUpdateProblem(Problem problem, String siteName, List<String> problemCategories) {
        Optional<Problem> existingProblem = problemRepo.findByProblemNumAndSiteName(problem.getProblemNum(), siteName);

        if (existingProblem.isPresent()) {
            // 문제가 이미 존재하면, 난이도와 algoScore 업데이트
            Problem updatedProblem = existingProblem.get();
            updatedProblem.updateLevel(problem.getProblemLevel(), getAlgoScoreForSite(problem.getProblemLevel(), siteName));
            return problemRepo.save(updatedProblem);
        } else {
            // 분류값이 들어올 경우
            if (problemCategories != null && !problemCategories.isEmpty()) {
                List<AlgoGroup> algoGroupList = new ArrayList<>();

                for (String algo : problemCategories) {
                    algoGroupList.add(AlgoGroup.builder()
                            .problem(problem)
                            .classification(algo).build());
                }

                algoGroupRepo.saveAll(algoGroupList);
            }

            // 문제가 존재하지 않으면, 새로운 문제 저장
            problem.updateSiteName(siteName);
            problem.updateLevel(problem.getProblemLevel(), getAlgoScoreForSite(problem.getProblemLevel(), siteName));
            return problemRepo.save(problem);
        }
    }

    private int getAlgoScoreForSite(String problemLevel, String siteName) {
        switch (siteName) {
            case "Baekjoon":
                return AlgoScoreUtil.getBojScore(Integer.parseInt(problemLevel));
            case "Programmers":
                return AlgoScoreUtil.getProgrammersScore(Integer.parseInt(problemLevel));
            case "Swea":
                return AlgoScoreUtil.getSweaScore(problemLevel);
            default:
                return 0; // 기본값 설정 또는 예외 처리
        }
    }
}
