package com.e1i5.backend.global.oauth;

import com.e1i5.backend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private String nameAttributesKey;
    private String nickname;
    private String email;
//    private Platform platform;
//    private User user;

//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey, User user) {
//        this.attributes = attributes;
//        this.nameAttributesKey = nameAttributesKey;
//        this.user = user;
//    }

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey, String nickname, String email) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.nickname = nickname;
        this.email = email;
//        this.platform = platform;
    }


    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if ("kakao".equals(socialName)) {
            return ofKakao("id", attributes);
        } else if ("google".equals(socialName)) {
            return ofGoogle("sub", attributes);
        }

        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        User user = User.builder()
//                .platform(Platform.GOOGLE)
//                .nickname(String.valueOf(attributes.get("name")))
//                .email(String.valueOf(attributes.get("email"))).build();
        return OAuthAttributes.builder()
                .nickname(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
//                .platform(Platform.GOOGLE)
                .attributes(attributes)
//                .user(user)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

//        User user = User.builder()
//                .platform(Platform.KAKAO)
//                .nickname(String.valueOf(kakaoProfile.get("nickname")))
//                .email(String.valueOf(kakaoAccount.get("email"))).build();
        return OAuthAttributes.builder()
                .nickname(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
//                .platform(Platform.KAKAO)
                .attributes(attributes)
//                .user(user)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

//    private static User buildUserFromAttributes(Map<String, Object> attributes) {
//
//        // User 객체를 생성하고 속성 설정
//        return User.builder()
//                .nickname(String.valueOf(attributes.get("name")))
//                .email(String.valueOf(attributes.get("email")))
//                .build();
//    }
}
