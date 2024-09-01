package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.user.dto.RandomNickname;
import com.e1i5.backend.domain.user.dto.response.NicknameResponse;
import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.exception.DuplicateNickname;
import com.e1i5.backend.domain.user.exception.UserNotFoundException;
import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.global.error.GlobalErrorCode;
import com.e1i5.backend.global.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    @Autowired
    private final AuthRepository authRepo;
    @Autowired
    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    public User findByEmail(String email) {
        return authRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findById(int userId) {
            return authRepo.findById(userId).orElseThrow(()
                    -> new IllegalArgumentException("Unexpected user")); //통일 예정
    }

    @Override
    public String createNewAccessToken(String refreshToken/*, HttpServletRequest httpServletRequest*/) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        // refresh token 유효성 검사
        try {
            tokenProvider.getClaims(refreshToken);
            // ExpiredJwtException 예외가 catch되지 않았을 때 AccessDeniedException
            return tokenProvider.createNewAccessToken(refreshToken);
        } catch (ExpiredJwtException e) {
            System.out.println("refresh token 재발급 오류" + e.getMessage());
            throw new UserNotFoundException(GlobalErrorCode.ACCESS_DENIED);
        }
    }

    /**
     * 닉네임 중복 검사
     * @param nickname 검사할 닉네임
     * @return true 사용 가능한 닉네임, false 이미 존재하는 닉네임
     */
    @Override
    public boolean duplicateCheckNickname(String nickname) {
        return !authRepo.existsByNickname(nickname);
    }

    /**
     * 닉네임 만드는 함수
     * @return 랜덤 닉네임
     */
    @Override
    public String generateRandomNickname() {
        Random random = new Random();

        while(true) {
            String adjective = RandomNickname.ADJECTIVES[random.nextInt(RandomNickname.ADJECTIVES.length)];
            String noun = RandomNickname.NOUNS[random.nextInt(RandomNickname.NOUNS.length)];
            String newNickname = adjective + " " + noun;

            if (duplicateCheckNickname(newNickname)) return newNickname;
        }
    }

    @Transactional
    @Override
    public String changeNickname(String nickname, int userId) {

        if (authRepo.existsByNickname(nickname)) { //이미 존재하는 닉네임이면
            throw new DuplicateNickname(GlobalErrorCode.DUPLICATE_NICKNAME);
        }

        User user = authRepo.findById(userId).orElseThrow(()
                -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
        user.updateNickname(nickname);

        return authRepo.save(user).getNickname();
    }

    @Override
    public NicknameResponse getLoginNickname(int userId) {

        return authRepo.findByUserId(userId).orElseThrow(()
                -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> withdrawUser(int userId) {
        User user = authRepo.findById(userId).orElseThrow(()
                -> new UserNotFoundException(GlobalErrorCode.USER_NOT_FOUND));
        user.withdrawUser(true, LocalDate.now());
        User updateUser = authRepo.save(user);

        if (updateUser.isStatus()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public boolean checkUserExistence(String nickname) {
        return authRepo.existsByNickname(nickname);
    }

}
