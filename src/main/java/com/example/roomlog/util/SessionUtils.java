package com.example.roomlog.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {

	// 세션에 담긴 userId 
    public static Long getUserId(HttpSession session) {
		Object userId = session.getAttribute("userId");
        return userId != null ? ((Number) userId).longValue() : -1;
    }

}
