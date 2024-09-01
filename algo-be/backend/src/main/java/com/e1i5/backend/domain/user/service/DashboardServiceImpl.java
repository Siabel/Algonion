package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.repository.ProblemRepository;
import com.e1i5.backend.domain.problem.repository.SolvedProblemRepository;
import com.e1i5.backend.domain.user.dto.response.AccCountGraphResponse;
import com.e1i5.backend.domain.problem.service.SolvedProblemService;
import com.e1i5.backend.domain.user.dto.response.AlgoScoreCountResponse;
import com.e1i5.backend.domain.user.dto.response.DashBoardProblemListResponse;
import com.e1i5.backend.domain.user.dto.response.StreakResponseInterface;
import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.repository.DashboardRepository;
import com.e1i5.backend.domain.user.repository.UserInfoRepository;
import com.e1i5.backend.global.util.TierUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserInfoRepository userInfoRepo;

    @Autowired
    private DashboardRepository dashboardRepo;

    @Autowired
    private SolvedProblemRepository solvedProblemRepo;

    @Autowired
    private ProblemRepository problemRepo;


    @Override
    public List<DashBoardProblemListResponse> getTop100ProblemsByNickname(String nickname) {
        int userId = getUserIdByNickname(nickname);

        List<Problem> solvedProblemsList = dashboardRepo.findProblemsByNicknameOrderedByAlgoScore(userId);

        // Entity를 Response로 변환

        return solvedProblemsList.stream()
                .map(DashBoardProblemListResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public int[] getAlgoScoreCountsByNickname(String nickname) {
        int userId = getUserIdByNickname(nickname);

        List<Object[]> algoScoreCounts = dashboardRepo.findAlgoScoreCountsByUserId(userId);

        // 알고리즘 점수에 대한 카운트를 Map으로 매핑
        Map<Integer, Integer> algoScoreMap = algoScoreCounts.stream()
                .collect(Collectors.toMap(
                        data -> Integer.parseInt(String.valueOf(data[0])),
                        data -> ((Number) data[1]).intValue(),
                        Integer::sum));

        // 특정 알고리즘 점수에 대한 배열 생성
        int[] responseArray = {
                algoScoreMap.getOrDefault(1, 0),
                algoScoreMap.getOrDefault(8, 0),
                algoScoreMap.getOrDefault(40, 0),
                algoScoreMap.getOrDefault(200, 0),
                algoScoreMap.getOrDefault(1000, 0),
                algoScoreMap.getOrDefault(3000, 0)
        };

        // 0보다 큰 경우에만 AlgoScoreCountResponse를 생성하여 리스트에 추가
        List<AlgoScoreCountResponse> responseList = Arrays.stream(responseArray)
                .filter(count -> count > 0)
                .mapToObj(AlgoScoreCountResponse::new)
                .collect(Collectors.toList());
        return responseArray;
    }

    @Override
    public int[] getCategoryCountsByNickname(String nickname) {
        int userId = getUserIdByNickname(nickname);

        List<Object[]> algoGroups = dashboardRepo.findAlgoGroupsByUserId(userId);

        // 각 문제별로 중복된 카테고리를 제거하고 개수를 세는 맵을 생성합니다.
        Map<Integer, Set<String>> categoryMap = new HashMap<>();
        for (Object[] group : algoGroups) {
            int problemId = (int) group[0];
            String classification = (String) group[1];
            categoryMap.computeIfAbsent(problemId, k -> new HashSet<>()).add(classification);
        }

        // 각 카테고리의 개수를 카운트합니다.
        Map<String, Long> categoryCounts = categoryMap.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 카테고리에 따른 카운트 배열을 생성합니다.
        int[] responseArray = {
                categoryCounts.getOrDefault("수학", 0L).intValue(),
                categoryCounts.getOrDefault("구현", 0L).intValue(),
                categoryCounts.getOrDefault("그리디 알고리즘", 0L).intValue(),
                categoryCounts.getOrDefault("문자열", 0L).intValue(),
                categoryCounts.getOrDefault("자료 구조", 0L).intValue(),
                categoryCounts.getOrDefault("그래프 이론", 0L).intValue(),
                categoryCounts.getOrDefault("다이나믹 프로그래밍", 0L).intValue(),
                categoryCounts.getOrDefault("기하학", 0L).intValue(),
        };

        // 카운트가 0보다 큰 경우에만 AlgoScoreCountResponse를 생성하여 리스트에 추가합니다.
        List<AlgoScoreCountResponse> responseList = Arrays.stream(responseArray)
                .filter(count -> count > 0)
                .mapToObj(AlgoScoreCountResponse::new)
                .collect(Collectors.toList());

        return responseArray;
    }

    private int getUserIdByNickname(String nickname) {
        return userInfoRepo.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found for nickname: " + nickname))
                .getUserId();
    }

    @Transactional
    public void updateUserScore(int userId, int problemId, int oldAlgoScore) {
        boolean isProblemSolvedByUser = solvedProblemRepo.existsByUser_UserIdAndProblem_ProblemId(userId, problemId);
        int userScore = userInfoRepo.findUserScoreByUserId(userId);
        int algoScore = problemRepo.findAlgoScoreByProblemId(problemId);
        if (!isProblemSolvedByUser) {
            // 사용자가 해당 문제를 푼 적이 없는 경우
            userInfoRepo.updateUserScore(userId, userScore + algoScore);
        } else {
            // 사용자가 해당 문제를 이미 푼 경우
            if (oldAlgoScore != algoScore) {
                // 알고리즘 점수가 변경된 경우
                userInfoRepo.updateUserScore(userId, userScore + (algoScore - oldAlgoScore));

                List<Integer> solvedUserIds = solvedProblemRepo.findDistinctUsersByProblem_ProblemId(problemId);

                for (Integer solvedUserId : solvedUserIds) {
                    boolean isSolvedByUser = solvedProblemRepo.existsByUser_UserIdAndProblem_ProblemId(solvedUserId, problemId);
                    int solvedUserScore = userInfoRepo.findUserScoreByUserId(solvedUserId);

                    if (isSolvedByUser) {
                        // 사용자가 해당 문제를 이미 푼 경우에만 점수를 업데이트합니다.
                        userInfoRepo.updateUserScore(solvedUserId, solvedUserScore + (algoScore - oldAlgoScore));
                    }
                    updateUserTier(solvedUserId);
                }
            }
        }
        updateUserTier(userId);
    }


    @Transactional
    public void updateUserTier(int userId) {
        int userScore = userInfoRepo.findUserScoreByUserId(userId);
        userInfoRepo.updateUserTier(userId, TierUtil.calculateTier(userScore));
    }

    /**
     * 스트릭 만드는 메서드
     * @param nickname
     * @return
     */
    @Override
    public Map<String, Long> makeStreak(String nickname, String startDate, String endDate) {

        int userId = getUserIdByNickname(nickname);
        List<StreakResponseInterface> streak =
                solvedProblemRepo.findSubmissionTimeAndCountByUserId(userId, endDate, startDate);

        LinkedHashMap<String, Long> result = new LinkedHashMap <>();
        for (StreakResponseInterface streakDate : streak) {
            result.put(streakDate.getSubmissionTime(), streakDate.getCount());
        }

        return result;
    }

    /**
     * 누적 문제 푼 개수 합 그래프
     * @param nickname 원하는 사용자의 그래프
     * @return 날짜 배열과 누적 합 배열을 담은 스트링 반환
     */
    public AccCountGraphResponse makeAccumulatedNumGraph(String nickname) {

        int totalDays = 365;

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(totalDays - 1);

        int userId = getUserIdByNickname(nickname);
        List<StreakResponseInterface> streak =
                solvedProblemRepo.findSubmissionTimeAndCountByUserId(userId, endDate.toString(), startDate.toString());

        // categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999]
        String[] categories = new String[totalDays];
        for (int i = 0; i < totalDays; i++) {
            categories[i] = startDate.plusDays(i).toString();
        }

        LinkedHashMap<String, Long> dateList = makeDateList(totalDays);

        for (StreakResponseInterface s : streak) {
            dateList.put(s.getSubmissionTime(), s.getCount());
        }

        // data: [30, 40, 45, 50, 49, 60, 70, 91]
        long[] data = new long[totalDays];
        long countSum = 0;

        for (int i = 0; i < totalDays; i++) {
            countSum += dateList.get(startDate.plusDays(i).toString());
            data[i] = countSum;
        }

        AccCountGraphResponse graphData = AccCountGraphResponse.builder()
                .categories(categories)
                .data(data)
                .build();

        return graphData;
    }

    /**
     * 누적 알고리즘 점수 그래프
     * @param nickname 원하는 사용자의 그래프
     * @return 날짜 배열과 누적 합 배열을 담은 스트링 반환
     */
    @Override
    public AccCountGraphResponse makeAccumulatedAlgoScoreGraph(String nickname) {
        int totalDays = 365;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(totalDays - 1);

        int userId = getUserIdByNickname(nickname);

        List<Object[]> firstSubmissionDatesAndAccumulatedAlgoScores = dashboardRepo.findFirstSubmissionDateAndAccumulatedAlgoScoreByUserId(userId);

        LinkedHashMap<String, Long> dateList = makeDateList(totalDays);
        long[] data = new long[totalDays];
        long accumulatedScore = 0;

        // 각 날짜마다 누적된 알고리즘 점수를 계산하여 맵에 저장
        for (Object[] entry : firstSubmissionDatesAndAccumulatedAlgoScores) {
            String submissionDate = entry[0] != null ? entry[0].toString() : null;
            int dailyAlgoScore = entry[1] != null ? Integer.parseInt(entry[1].toString()) : 0;

            // 현재 날짜까지의 누적 점수를 계산하되, 0인 경우에도 이전 누적 점수를 사용
            accumulatedScore = Math.max(accumulatedScore + dailyAlgoScore, accumulatedScore);

            // 현재 날짜까지의 누적 점수를 맵에 저장
            if (submissionDate != null && dateList.containsKey(submissionDate)) {
                dateList.put(submissionDate, accumulatedScore);
            }
        }

        // 누적된 점수 배열 초기화
        int index = 0;
        for (Map.Entry<String, Long> entry : dateList.entrySet()) {
            data[index++] = entry.getValue();
        }

        // 누적된 점수 배열의 중간에 있는 0을 앞에 있는 누적된 값으로 채움
        for (int i = 1; i < totalDays; i++) {
            if (data[i] == 0) {
                data[i] = data[i - 1];
            }
        }

        // 날짜 배열 초기화
        String[] categories = dateList.keySet().toArray(new String[0]);

        return AccCountGraphResponse.builder()
                .categories(categories)
                .data(data)
                .build();
    }




    /**
     * 통계를 위해 날짜 데이터를 만드는 함수
     * @param totalDays 원하는 날짜 총일수
     * @return key는 날짜, value는 문제 푼 개수
     */
    public LinkedHashMap<String, Long> makeDateList(int totalDays) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(totalDays - 1); // 365일이면 364를 입력

        LinkedHashMap <String, Long> result = new LinkedHashMap <>();
        for (int i = 0; i < totalDays; i++) {
            result.put(startDate.plusDays(i).toString(), 0L);
        }

        return result;
    }
}