package com.e1i5.backend.global.oauth;

import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final AuthRepository authRepo;
    private final AuthService authService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        CustomOAuth2User customOAuth2User = saveOrUpdate(user, registrationId);
        System.out.println("CustomService user: " + user.toString());
        System.out.println("CustomService customOAuth2User: " + customOAuth2User.toString());
        return customOAuth2User;
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private CustomOAuth2User  saveOrUpdate(OAuth2User oAuth2User, String registrationId) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, attributes);

        String email = (String) oAuthAttributes.getEmail();
        String name = (String) oAuthAttributes.getNickname();
//        Platform platform = oAuthAttributes.getPlatform();

        System.out.println("email: " + email);
        System.out.println(name);
        String nickname = authService.generateRandomNickname();

        User user = authRepo.findByEmail(email) // email로 이미 회원가입 여부 확인
//                .map(entity -> entity.updateNickname(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(nickname)
                        .regDate(LocalDate.now())
                        .build());
        authRepo.save(user);
        return new CustomOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                oAuthAttributes.getNameAttributesKey(),
                email,
                nickname
//                platform
        );
    }
}
