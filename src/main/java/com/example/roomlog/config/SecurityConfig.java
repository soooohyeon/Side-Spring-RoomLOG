package com.example.roomlog.config;

import com.example.roomlog.config.auth.CustomOAuth2UserService;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.user.SocialTypeRepository;
import com.example.roomlog.repository.user.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
    CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	SocialTypeRepository socialTypeRepository;
	@Autowired
	UserRepository userRepository;
	
	// 허용할 경로 설정
    public static final String[] allowUrls = {
    	"/", "/main",
    	"/login", "/oauth2/**", "/login/oauth2/**",
    	"/profile/**",
    	"/notice/**",
    	"/community/community-list", "/community/community-view",
        "/comment/comment-list/**",
    	"/upload/**",
    	"/css/**", "/js/**", "/image/**"
    };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	        	// allowUrls에 저장된 경로의 요청은 허용
	            .requestMatchers(allowUrls).permitAll()
	            // 그 외 요청은 인증 필요
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .permitAll()
	        )
	        .oauth2Login(oauth2 -> oauth2
	        	.loginPage("/login")
	        	.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
	        	.successHandler((request, response, authentication) -> {
	        		HttpSession session = request.getSession();
	        	    Boolean isNewUser = (Boolean) session.getAttribute("isNewUser");
	        	    
	        	    if (isNewUser != null && isNewUser) {
	        	    	response.sendRedirect("/login/join-required");
	        	    } else if (isNewUser != null && !isNewUser) {
	        	        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
	        	        String email = "";
	        	        String socialTypeName = (String) session.getAttribute("oauthSocialType");
	        	        
	        	        // 이메일 가져오기
	        	        if ("KAKAO".equals(socialTypeName)) {
	        	            Map<String, Object> kakaoAccount = (Map<String, Object>) oauth2User.getAttribute("kakao_account");
	        	            email = (String) kakaoAccount.get("email");
	        	        } else if ("GOOGLE".equals(socialTypeName)) {
	        	            email = (String) oauth2User.getAttribute("email");
	        	        } else {
	        	            throw new OAuth2AuthenticationException("Unsupported provider: " + socialTypeName);
	        	        }

	        	        SocialType socialType = socialTypeRepository.findBySocialTypeName(socialTypeName);
	        	        User user = userRepository.findByUserEmailAndSocialType(email, socialType).get();

	        	        session.setAttribute("userId", user.getUserId());
	        	        session.setAttribute("userRole", user.getUserRole());
	        	    	response.sendRedirect("/main");
	        	    }
	        	})
	        )
	        .logout(logout -> logout
	        	.logoutUrl("/logout") 
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID", "remember-me", "access_token", "refresh_token")
	            .logoutSuccessHandler((req, res, auth) -> {
	            	new SecurityContextLogoutHandler().logout(req, res, auth);
	                res.sendRedirect("/main?logout=true");
	            })
	        )
	        .csrf(csrf -> csrf.disable());

	    return http.build();
	}

}
