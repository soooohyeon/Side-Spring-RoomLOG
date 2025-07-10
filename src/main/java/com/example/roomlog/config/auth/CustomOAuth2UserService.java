package com.example.roomlog.config.auth;

import java.util.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);
        // 소셜 로그인 종류를 저장하기 위한 변수
        String socialType = request.getClientRegistration().getRegistrationId();

        // Kakao 계정 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        // 이미 회원가입된 상태인지 이메일로 DB 조회
        Optional<User> findUser = userRepository.findByUserEmail(email);

        // 세션 등에 사용될 기본 OAuth2User 만들기
        DefaultOAuth2User principal = new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.getAttributes(),
            "id"
        );

        // 가입 여부를 세션에 저장
        HttpServletRequest requestObj = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = requestObj.getSession();
        session.setAttribute("isNewUser", !findUser.isPresent());
        session.setAttribute("oauthNickname", nickname);
        session.setAttribute("oauthEmail", email);

        return principal;
    }
}
