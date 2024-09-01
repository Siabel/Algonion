package com.e1i5.backend.domain.user.service;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @DisplayName("generateNickname(): 랜덤한 닉네임 생성")
    @Test
    void generateNickname() {
        // given

        // when

        // then
        for (int i = 0; i < 50; i++) {
            String nickname = authService.generateRandomNickname();
            System.out.println(nickname);
        }
    }
}