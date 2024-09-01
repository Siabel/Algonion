package com.e1i5.backend.global.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드에 붙어있으면 사용자 권한을 인증 필수
 */
//@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('ROLE_USER')")
// 런타임 까지 유지
@Retention(RetentionPolicy.RUNTIME)
// 타겟은 파라미터에만 붙이겠다.
@Target(ElementType.PARAMETER)
// 익명 사용자인 경우에는 null로, 익명 사용자가 아닌 경우에는 실제 account 객체로
// Principal 을 다이나믹 하게 꺼내기 위해 @CurrentUser 생성
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface AuthUser {
}
