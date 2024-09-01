package com.e1i5.backend.global.jwt;

import com.e1i5.backend.domain.user.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * JWT 토큰 생성과 관련된 서비스
 */
@Service
@Slf4j
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "ROLE";
    @Value("${jwt.secret-key}")
    private String SECRET_KEY; // jwt 비밀키

    @Value("${jwt.access-token-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION; // 60초 * 30 -> 30분

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION; // 60초 * 60 * 24 * 7 -> 2주


    /**
     * JWT 라이브러리를 통해서 액세스 토큰을 생성하는 메소드
     * @param user: 로그인하는 사용자 정보
     * @return 생성된 액세스 토큰
     */
    public String createAccessToken(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId())) // // 내용 sub : 유저의 이메일
//                .claim(AUTHORITIES_KEY, role) // role
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 서명 : 비밀값과 함께 해시값을 HS512 방식으로 암호화
                .compact();
    }

    /**
     * JWT 라이브러리를 통해서 리프레시 토큰을 생성하는 메소드
     * @param user: 로그인하는 사용자 정보
     * @return 생성된 리프레시 토큰
     */
    public String createRefreshToken(User user) {
        //TODO 토큰의 값을 uuid 또는 jwt
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        Claims claims = getClaims(refreshToken);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(String.valueOf(claims.getSubject()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * JWT 토큰에서 인증 정보 조회
     * @param token
     * @return 사용자 이메일이 들어 있는 토큰 제목 sub와 토큰 기반으로 인증 정보 반환
     */

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token); // claim에 권한을 넣고 밑에 값을 claim에 있는 권한값으로 변경
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject
                (), "", authorities), token, authorities);
    }

    /**
     * refreshToken으로 쿠키 설정
     * @param refreshToken
     * @return 리프레시 토큰을 담은 쿠키 반환
     */
    public ResponseCookie createSetCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .sameSite("None")
                .httpOnly(false) // 원래는 true이나 우리 서비스는 확장 프로그램에서 쿠키에 있는 값을 써야하기 때문에
                .secure(true) // https를 사용하면 true
                .maxAge(REFRESH_TOKEN_EXPIRATION)
                .build();
    }
}
