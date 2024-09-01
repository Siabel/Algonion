package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.problem.response.ProblemResponse;
import com.e1i5.backend.domain.user.dto.response.UserInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    void saveUserProfile(int userId, MultipartFile profileImg);
    Map<String, Long> getUserSevenStreak(int userId);
    UserInfoResponse getUserInfo(int userId, String nickname);
//    UserInfoResponse getUserInfo(String nickname);
    List<ProblemResponse> recommandProblem(int userId);
}
