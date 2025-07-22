package com.example.roomlog.config.auth;

import java.util.*;

import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.user.SocialTypeRepository;
import com.example.roomlog.repository.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final SocialTypeRepository socialTypeRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String email = "";
        
        // Spring 기본 OAuth2 유저 서비스가 Kakao/Google 서버에서 사용자 정보 받아옴
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        
        // 구글인지 카카오인지 구분
        String socialTypeName = userRequest.getClientRegistration().getRegistrationId(); // "google", "kakao"
        // 로그인한 유저 정보 전체를 담음
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 사용자 정보 추출
        if ("kakao".equals(socialTypeName)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        } else if ("google".equals(socialTypeName)) {
            email = (String) attributes.get("email");
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + socialTypeName);
        }

        // 이미 회원가입된 상태인지 DB 조회
        SocialType socialType = socialTypeRepository.findBySocialTypeName(socialTypeName.toUpperCase());
        Optional<User> userOpt = userRepository.findByUserEmailAndSocialType(email, socialType);
        boolean isNewUser = userOpt.isEmpty();

        // 가입 여부를 세션에 저장
        HttpServletRequest requestHttp = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = requestHttp.getSession();
        session.setAttribute("isNewUser", isNewUser);
        if (isNewUser) {
        	session.setAttribute("oauthEmail", email);
        }
        session.setAttribute("oauthSocialType", socialTypeName.toUpperCase());
        
        // (기존 유저면) UserEntity → OAuth2User 로 변환해서 리턴
        return oAuth2User;
    }
}
