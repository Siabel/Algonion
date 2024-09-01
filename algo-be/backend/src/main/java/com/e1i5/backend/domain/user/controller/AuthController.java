package com.e1i5.backend.domain.user.controller;

import com.e1i5.backend.domain.user.dto.request.NicknameRequest;
import com.e1i5.backend.domain.user.dto.response.CreateAccessTokenResponse;
import com.e1i5.backend.domain.user.dto.response.NicknameResponse;
import com.e1i5.backend.domain.user.exception.UserNotFoundException;
import com.e1i5.backend.domain.user.service.AuthService;
import com.e1i5.backend.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/user")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(HttpServletRequest httpServletRequest) {
        // 1. 쿠키에서 refresh token 가져오기
        String refreshToken = CookieUtil.findCookie(httpServletRequest, "refresh_token");

        // 2. refresh token을 사용하여 새로운 access token 생성 또는 기능 수행
        String newAccessToken = authService.createNewAccessToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @Operation(summary = "닉네임 가져오기")
    @GetMapping("/login-nickname")
    public ResponseEntity<NicknameResponse> getLoginNickname(Principal principal) {
//        int userId = 1;
        int userId = Integer.parseInt(principal.getName());
        NicknameResponse loginNickname = authService.getLoginNickname(userId);

        return new ResponseEntity<>(loginNickname, HttpStatus.OK);
    }

    /**
     * 닉네임 중복검사
     * @param nicknameReq
     * @return true 사용가능, false 사용불가능
     */
    @Operation(summary = "닉네임 중복검사")
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody NicknameRequest nicknameReq) {
        //TODO @Valid 유효성 검사 추가
        boolean checkResult = authService.duplicateCheckNickname(nicknameReq.getNickname());

        return new ResponseEntity<>(checkResult, HttpStatus.OK);
    }

    /**
     * 닉네임 변경
     * @param nicknameReq
     * @return
     */
    @Operation(summary = "닉네임 변경")
    @PutMapping("/nickname")
    public ResponseEntity<String> changeNickname(@RequestBody NicknameRequest nicknameReq, Principal principal) {
        int userId = Integer.parseInt(principal.getName());
        //TODO @Valid 유효성 검사 추가
        String changeNickname = authService.changeNickname(nicknameReq.getNickname(), userId);

        return new ResponseEntity<>(changeNickname, HttpStatus.OK);
    }

    @Operation(summary = "로그인 테스트")
    @GetMapping("/login-test")
    public ResponseEntity<String> loginTest() {
        System.out.println("login test입니당");
        return new ResponseEntity<String>("test", HttpStatus.OK);
    }


    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/withdraw")
    public ResponseEntity<String> withdrawUser() {
        try {
            int userId = 1;
            authService.withdrawUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-user")
    public ResponseEntity<Boolean> checkUserExistence(@RequestParam String nickname) {
        boolean userExists = authService.checkUserExistence(nickname);
        return ResponseEntity.ok(userExists);
    }

}
