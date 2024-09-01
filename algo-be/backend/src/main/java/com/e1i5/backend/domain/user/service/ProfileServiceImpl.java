package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.problem.model.entity.Problem;
import com.e1i5.backend.domain.problem.repository.ProblemRepository;
import com.e1i5.backend.domain.problem.repository.SolvedProblemRepository;
import com.e1i5.backend.domain.problem.response.ProblemInterfaceResponse;
import com.e1i5.backend.domain.problem.response.ProblemResponse;
import com.e1i5.backend.domain.user.dto.response.StreakResponseInterface;
import com.e1i5.backend.domain.user.dto.response.UserInfoResponse;
import com.e1i5.backend.domain.user.entity.ProfileFileInfo;
import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.exception.UserNotFoundException;
import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.domain.user.repository.UserProfileRepository;
import com.e1i5.backend.global.error.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Value("${file.path.nodeValue}")
    private String UPLOAD_PATH;

    private final UserProfileRepository userProfileRepo;
    private final SolvedProblemRepository solvedProblemRepo;
    private final AuthRepository authRepo;
    private final ProblemRepository problemRepo;
    private final FriendService friendService;

    @Override
    public void saveUserProfile(int userId, MultipartFile profileImg) {
        //TODO File값이 null일 때 처리
        //TODO File 확장자도 검사
        //TODO 파일 저장 실패시 에러 처리
        String saveFileName = "";
        String originalFileName = "";
        if (profileImg != null) {
            File folder = new File(UPLOAD_PATH);

            if (!folder.exists())
                folder.mkdirs();

            originalFileName = profileImg.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                saveFileName = UUID.randomUUID().toString()
                        + originalFileName.substring(originalFileName.lastIndexOf('.'));

                try {
                    profileImg.transferTo(new File(folder, saveFileName));
                } catch (IllegalStateException e) {
                    System.out.println("파일 업로드 에러");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("파일 업로드 에러");
                    e.printStackTrace();
                }
            }
        }

        User user = authRepo.findById(userId).orElseThrow(()-> new IllegalArgumentException("Unexpected user"));

        userProfileRepo.save(ProfileFileInfo.builder()
                .saveFile(saveFileName)
                .originalFile(originalFileName)
                .user(user).build());
    }

    /**
     * 확장 프로그램 버전 7일 스트릭
     * @param userId
     * @return
     */
    @Override
    public Map<String, Long> getUserSevenStreak(int userId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        List<StreakResponseInterface> streak =
                solvedProblemRepo.findSubmissionTimeAndCountByUserId(userId, endDate.toString(), startDate.toString());

        LinkedHashMap <String, Long> result = new LinkedHashMap <>();
        for (int i = 0; i < 7; i++) {
            result.put(startDate.plusDays(i).toString(), 0L);
        }

        for (StreakResponseInterface streakDate : streak) {
            result.put(streakDate.getSubmissionTime(), streakDate.getCount());
        }

        return result;
    }

    /**
     * 사용자 프로필 정보 불러오기
     * @param userId 로그인한 사용자 인덱스,, friendNickname 사용자 닉네임
     * @return 사용자 아이디, 티어, 점수, 프로필 이미지, 문제 푼 개수
     */
    @Override
    public UserInfoResponse getUserInfo(int userId, String friendNickname) {

        log.info("ProfileServiceImpl getUserInfo 진입 userId: {}, nickname: {}", userId, friendNickname);

//        int userId = getUserIdByNickname(nickname);
//        UserInfoResponse user = authRepo.getUserInfoByUserId(nickname)
//                .orElseThrow(() -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));

        int findFriendUserId = getUserIdByNickname(friendNickname);

        User loginUser = authRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
        User friend = authRepo.findById(findFriendUserId).orElseThrow(() -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));

        Long problemCount = solvedProblemRepo.countUserSolvedProblem(findFriendUserId);
        int checkFriendship = friendService.checkFriendship(loginUser, friend);

        return UserInfoResponse.builder()
                .score(friend.getUserScore())
                .tier(friend.getTier())
                .userId(friend.getUserId())
                .problemCount(problemCount)
                .friendship(checkFriendship)
                .build();
    }

    @Override
    public List<ProblemResponse> recommandProblem(int userId) {

        int[] unsolvedProblemsByUserId = problemRepo.findUnsolvedProblemsIdsByUserId(userId);

        // 랜덤 숫자 뽑기
        Random random = new Random();

        int size = unsolvedProblemsByUserId.length;
        int num1 = random.nextInt(size) + 1;
        int num2 = random.nextInt(size) + 1;

        while (num1 == num2) {
            num2 = random.nextInt(size) + 1;
        }

        List<Integer> problemIds = Arrays.asList(num1, num2);
        System.out.println("num 1, num 2: " + num1 + " " + num2);
        List<ProblemInterfaceResponse> problemInterface = problemRepo.findByProblemIdIn(problemIds);

        List<ProblemResponse> recommandProblem = new ArrayList<>();
        for (ProblemInterfaceResponse p : problemInterface) {
            ProblemResponse problem = ProblemResponse.builder()
                    .siteName(p.getSiteName())
                    .problemNum(p.getProblemNum())
                    .problemTitle(p.getProblemTitle())
                    .problemLevel(p.getProblemLevel())
                    .url(p.getUrl())
                    .build();
            recommandProblem.add(problem);
        }

        return recommandProblem;
    }

    private int getUserIdByNickname(String nickname) {

        return authRepo.findUserIdByNickname(nickname)
                .orElseThrow(() -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND))
                .getUserId();
    }

}
