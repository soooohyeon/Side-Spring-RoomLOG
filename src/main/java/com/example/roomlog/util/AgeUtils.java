package com.example.roomlog.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeUtils {

	// 나이대 계산
	public static String getAgeLabel(int isAgeVisible, LocalDate birth) {
		String result = "";
	    if (isAgeVisible == 0) {
	    	result = "비공개";
	    } else if (isAgeVisible == 1) {
	    	int age = Period.between(birth, LocalDate.now()).getYears();
	    	int ageGroup = (age / 10) * 10;
	    	result = ageGroup + "대";
	    }

	    return result;
	}
	
}
