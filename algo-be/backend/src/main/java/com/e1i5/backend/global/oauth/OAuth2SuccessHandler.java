package com.e1i5.backend.global.oauth;

import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.domain.user.service.AuthServiceImpl;
import com.e1i5.backend.global.jwt.TokenProvider;
import com.e1i5.backend.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION; // 60초 * 60 * 24 * 7 -> 2주
    @Value("${jwt.access-token-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION;
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final String REDIRECT_PATH = "https://algonion.store/login/success";
//    public static final String REDIRECT_PATH = "https://localhost/login/oauth_google_check";

    private final TokenProvider tokenProvider;
    private final AuthRepository userRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final AuthServiceImpl userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        System.out.println("로그인한 유저 email: " + oAuth2User.getEmail());
        User user = userService.findByEmail(oAuth2User.getEmail());

        // 리프레시 토큰 생성 -> 저장 -> 쿠키에 사용
        String refreshToken = tokenProvider.createRefreshToken(user);

        addRefreshTokenToCookie(request, response, refreshToken);
        //액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.createAccessToken(user);
        addAccessTokenToCookie(request, response, accessToken);

        String targetUrl = getTargetUrl();
        // 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);
        // 리다이렉트

//        ResponseCookie cookie= ResponseCookie.from("refresh_token", refreshToken).maxAge(30 * 60 * 60).path("/").domain("localhost").httpOnly(false).secure(true).sameSite("None").build();
////        ResponseCookie cookie= ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).secure(true).maxAge(30 * 60 * 60).path("/").domain("192.168.31.177").sameSite("None").build();
//        response.addHeader("Set-Cookie", cookie.toString());

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    // 생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_EXPIRATION;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }
    private void addAccessTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) ACCESS_TOKEN_EXPIRATION;

        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }



    // 인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    // 액세스 토큰을 패스에 추가
    private String getTargetUrl() {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .build()
                .toUriString();
    }
}
