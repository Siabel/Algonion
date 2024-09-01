package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.user.dto.response.NicknameResponse;
import com.e1i5.backend.domain.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    User findByEmail(String email);
    User findById(int userId);
    String createNewAccessToken(String refreshToken);
    boolean duplicateCheckNickname(String nickname);
    String generateRandomNickname();
    String changeNickname(String nickname, int userId);
    ResponseEntity<Void> withdrawUser(int userId);
    NicknameResponse getLoginNickname(int userId);
    boolean checkUserExistence(String nickname);
}
