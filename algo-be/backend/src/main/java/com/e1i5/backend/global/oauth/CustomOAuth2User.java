package com.e1i5.backend.global.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * 소셜 로그인을 구글말고도 여러개 쓸려고 할 때 들어오는 값이 달라서 맞추기 위해 사용
 * loadUser 메서드가 반환하는 값이 OAuth2User이기 때문에 여기에 email, nickname, platform 정보를 추가하여
 * 값을 반환하기 위한 클래스 -> DefaultOAuth2User 클래스를 확장
 * OAuth2UserCustomService에서 loadUser 메서드가 반환하는 OAuth2User에
 * email, nickname, platform 정보를 추가하려면, DefaultOAuth2User 클래스를 확장
 */
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final String nickname;
//    private final Platform platform;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            String email,
                            String nickname) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.nickname = nickname;
//        this.platform = platform;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

//    public Platform getPlatform() {
//        return platform;
//    }
}

