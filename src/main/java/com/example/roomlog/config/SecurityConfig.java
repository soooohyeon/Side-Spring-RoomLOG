package com.example.roomlog.config;

import com.example.roomlog.config.auth.CustomOAuth2UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
	
	// 허용할 경로 설정
    public static final String[] allowUrls = {
    	"/", "/main",
    	"/login", "/oauth2/**", "/login/oauth2/**",
    	"/css/**", "/js/**", "/image/**"
    };

    SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }
	
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
	        	    } else {
	        	    	response.sendRedirect("/main");
	        	    }
	        	})
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/main")
	        )
	        .csrf(csrf -> csrf.disable());

	    return http.build();
	}

}
