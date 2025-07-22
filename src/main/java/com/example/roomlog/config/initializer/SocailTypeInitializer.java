package com.example.roomlog.config.initializer;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.repository.user.SocialTypeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocailTypeInitializer implements ApplicationRunner {

	private final SocialTypeRepository socialTypeRepository;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
        if (socialTypeRepository.count() == 0) {
            Arrays.asList(
                new SocialType(1, "KAKAO"),
                new SocialType(2, "GOOGLE"),
                new SocialType(3, "NAVER")
            ).forEach(socialTypeRepository::save);
        }
	}

}
