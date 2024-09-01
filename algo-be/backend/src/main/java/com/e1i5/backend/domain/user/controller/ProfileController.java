package com.e1i5.backend.domain.user.controller;


import com.e1i5.backend.domain.problem.response.ProblemResponse;
import com.e1i5.backend.domain.user.dto.response.ExtUserInfoResponse;
import com.e1i5.backend.domain.user.dto.response.UserInfoResponse;
import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.service.AuthService;
import com.e1i5.backend.domain.user.service.DashboardService;
import com.e1i5.backend.domain.user.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/profile")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService; //TODO 이 방식 또는 RequiredArguments 방식으로 통일
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private AuthService authService;

    @Operation(summary = "프로필 이미지 저장")
    @PostMapping("/profile-img")
    public ResponseEntity<Void> saveUserProfile(MultipartFile profileImg, Principal principal) {
        int userId = Integer.parseInt(principal.getName());
        profileService.saveUserProfile(userId, profileImg);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 1년치 스트릭 만드는 메서드
     * @param nickname
     * @return 날짜와 그 날짜에 푼 문제 개수 리스트 반환
     */
    @Operation(summary = "1년치 스트릭 만드는 메서드")
    @GetMapping("/streak")
    public ResponseEntity<Map<String, Long>> getAllStreak(
            @RequestParam("nickname") String nickname,
            @RequestParam("from") String startDate,
            @RequestParam("to") String endDate) {

        Map<String, Long> streakResponses = dashboardService.makeStreak(nickname, startDate, endDate);

        return new ResponseEntity<>(streakResponses, HttpStatus.OK);
    }

    /**
     * 7일 스트릭 만드는 메서드(확장 프로그램 ver)
     * @return
     */
    @Operation(summary = "7일 스트릭 만드는 메서드(확장 프로그램 ver)")
    @GetMapping("/ext")
    public ResponseEntity<ExtUserInfoResponse> getExtUserInfo(Principal principal) {

        int userId = Integer.parseInt(principal.getName());

        Map<String, Long> streakResponses = profileService.getUserSevenStreak(userId); //TODO 서비스 단으로 변경

        User user = authService.findById(userId);
        ExtUserInfoResponse userInfo = ExtUserInfoResponse.builder()
                .tier(user.getTier())
                .nickname(user.getNickname())
                .streak(streakResponses).build();

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    /**
     * 사용자 정보 불러오기 
     * @param nickname 사용자 닉네임
     * @return 사용자 아이디, 티어, 점수, 프로필 이미지
     */
    @Operation(summary = "사용자 정보 불러오기 ")
    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam("nickname") String nickname, Principal principal) {
        int userId = Integer.parseInt(principal.getName());
        UserInfoResponse user = profileService.getUserInfo(userId, nickname);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "문제 추천하기")
    @GetMapping("/recommand")
    public ResponseEntity<?> recommandLevelProblem() {

        List<ProblemResponse> problemResponses = profileService.recommandProblem(1);

        return new ResponseEntity<>(problemResponses, HttpStatus.OK);
    }
}
