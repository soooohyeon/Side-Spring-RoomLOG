package com.example.roomlog.dto.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Criteria {
    private int page;			// 현재 페이지
    private int amount;			// 한 페이지 당 게시물 수
    private String category;	// 검색어 키워드
    private String keyword;		// 검색어
    private String sort;		// 정렬
    
    public Criteria() {
        this.page = 1;
        this.amount = 3;
        this.sort = "newest";
    }
    
    public Criteria(String category, String keyword, String sort) {
        this(1, 3, category, keyword, sort);
    }

	public Criteria(int page, int amount, String category, String keyword, String sort) {
		this.page = page;
		this.amount = amount;
		this.category = category;
		this.keyword = keyword;
	    this.sort = (sort == null) ? "newest" : sort;
	}
    
}
