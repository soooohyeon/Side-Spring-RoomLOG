package com.example.roomlog.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Criteria {
    private int page;			// 현재 페이지
    private int amount;			// 한 페이지 당 게시물 수
    private String category;	// 검색어 키워드
    private String keyword;		// 검색어
    private String sort;		// 정렬
    
    public Criteria(String category, String keyword, String sort) {
        this(1, 4, category, keyword, sort);
    }

	public Criteria(int page, int amount, String category, String keyword, String sort) {
		this.page = page;
		this.amount = amount;
		this.category = category;
		this.keyword = keyword;
	    this.sort = (sort == null) ? "newest" : sort;
	}
    
}
