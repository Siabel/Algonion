package com.e1i5.backend.global.jwt;

import com.e1i5.backend.global.error.GlobalErrorCode;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 인증 정보 설정
 * : 요정 헤더에서 키가 'Authorization'인 필드의 값을 가져온 다음, 토큰의 접두사 'Bearer'을 제외한 값을 얻는다.
 * 만약 값이 null이거나 Bearer로 시작하지 않으면 null을 반환한다.
 * 이어서 가져온 토큰이 유효한지 확인하고 유효하다면 인증 정보를 관리하는 시큐리티 컨텍스트에 인증 정보를 설정한다.
 */

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("토큰 인증 필터 구간");
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

//        if (token == null) {
//            log.error("엑세스 토큰이 비어있습니다");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 존재하지 않습니다");
//            return;
//        }

        Claims claims;
//        try {
//            claims = tokenProvider.getClaims(token);
//        } catch (SignatureException ex) {
//            log.error("Unsupported Signature");
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//            return;
//         } catch (MalformedJwtException ex) {
//            log.error("Invalid JWT token");
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//            return;
//        } catch (ExpiredJwtException ex) {
//            log.error("Expired JWT token");
//            // access token이 만료됐을 때
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
////            response.sendError(GlobalErrorCode.TOKEN_EXPIRED.getStatus(), GlobalErrorCode.TOKEN_EXPIRED.getMessage());
//            return;
//        } catch (UnsupportedJwtException ex) {
//            log.error("Unsupported JWT token");
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//            return;
//        } catch (IllegalArgumentException ex) {
//            log.error("JWT claims string is empty.");
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//            return;
//        } catch (Exception ex) {
//            log.error(ex.toString());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//            return;
//        }

        try {
            claims = tokenProvider.getClaims(token);
        } /*catch (ExpiredJwtException e) {
            log.info("expired access token: {}", e.getMessage());
            request.setAttribute("exception", GlobalErrorCode.TOKEN_EXPIRED.getMessage());
//            response.sendError(401, "토큰 만료");
            filterChain.doFilter(request, response);
            return;
        } */catch (Exception e) {
            log.info("jwt exception message : {} token : {}", e.getMessage(), token);
//            response.sendError(400, "토큰 유효하지 않음");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("Invalid token: Signature verification failed");
            filterChain.doFilter(request, response);
            return;
        }

//        catch (ExpiredJwtException e) {
//            log.error("expired access token", e.getMessage());
//            response.sendError(401, "토큰 만료");
////            request.setAttribute("exception", "토큰 만료");
////            filterChain.doFilter(request, response);
//            return;
//
////            log.error("Expired JWT token");
////            // access token이 만료됐을 때
////            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
////            return;
//
//        } catch (Exception e) {
//            log.info("jwt exception message : {} token : {}", e.getMessage(), token);
//            response.sendError(401, "유효하지 않은 토큰");
////            filterChain.doFilter(request, response);
//            return;
//        }

        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보 설정
        filterChain.doFilter(request, response);
    }

    /**
     * 헤더로부터 token 받아오는 메서드
     * @param
     * @return 토큰값
     */
    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}