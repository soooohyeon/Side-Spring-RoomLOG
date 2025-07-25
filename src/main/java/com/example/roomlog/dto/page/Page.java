package com.example.roomlog.dto.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Page {
	private int pageCount;		// 페이지 세트당 표시될 수
	private int startPage;		// 페이지 세트의 시작 숫자
	private int endPage;		// 페이지 세트의 마지막 숫자
	private int realEnd;		// 실제 가장 마지막 페이지
	private boolean prev;		// 이전 버튼 표시 여부
	private boolean next;		// 다음 버튼 표시 여부
	private int total;			// 전체 게시글 수
	private Criteria criteria;	// 화면에서 전달받은 page, amount를 저장하는 객체

    public Page(Criteria criteria, int total) {
        this(criteria, total, 5);
    }

	public Page(Criteria criteria, int total, int pageCount) {
		this.criteria = criteria;
		this.total = total;
		this.pageCount = pageCount;
		
		// 현재 페이지를 기준으로 세트의 시작과 마지막 번호 구하기
		this.endPage = (int)(Math.ceil(criteria.getPage() / (double)pageCount)) * pageCount;
		this.startPage = endPage - pageCount + 1;
		
		// 게시글 전체 수로 실제 마지막 페이지 구하기
		this.realEnd = (int)Math.ceil((double)total / criteria.getAmount());
		
		// 세트의 마지막 번호보다 실제 마지막 페이지가 작다면
		if (realEnd < endPage) {
			// 세트의 마지막 번호를 실제 마지막 페이지로 교체
			this.endPage = realEnd == 0 ? 1 : realEnd;
		}
		this.prev = startPage > 1;
		this.next = endPage < realEnd;
	}
}
