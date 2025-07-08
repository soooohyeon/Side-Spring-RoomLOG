package com.example.roomlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DBConnectCheck {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void checkConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
            System.out.println("DB 연결 성공! MariaDB 버전 : " + result);
        } catch (Exception e) {
            System.out.println("DB 연결 실패 : " + e.getMessage());
        }
    }
}
