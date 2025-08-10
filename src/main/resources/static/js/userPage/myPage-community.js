// errMsg는 basic.js
// 페이지 기본은 1
let registPage = 1;
let scrapPage = 1;

// ---------------------------------------------------------------

loadWriteData(registPage);

// 내가 작성한 게시글
function loadWriteData(page) {
	fetch(`/mypage/community/regist-list?page=${page}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("내가 작성한 게시글 렌더링 실패");
		return response.json();
	})
	.then((data) => { showWriteList(data); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); });
}
	
function showWriteList(data) {
	const $countDiv = $(".span-regist-count");
	const $wrap = $(".write-list-wrap");
	const $pageWrap = $wrap.find("#DIV-PAGENATION-WRAP");
	
	// 작성한 게시글 개수 표시
	$countDiv.html(data.registCount);
	// 내가 작성한 게시글 목록
	renderList("regist", $wrap, data.registList, data.registCount);
	// 페이징 처리
	showPage($pageWrap, data.page);
}
// ---------------------------------------------------------------

loadScrapData(scrapPage);

// 내가 작성한 게시글
function loadScrapData(page) {
	fetch(`/mypage/community/scrap-list?page=${page}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("내가 스크랩한 게시글 렌더링 실패");
		return response.json();
	})
	.then((data) => { showScrapList(data); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); });
}
	
function showScrapList(data) {
	const $countDiv = $(".span-scrap-count");
	const $wrap = $(".scrap-list-wrap");
	const $pageWrap = $wrap.find("#DIV-PAGENATION-WRAP");
	
	// 작성한 게시글 개수 표시
	$countDiv.html(data.scrapCount);
	// 내가 작성한 게시글 목록
	renderList("scrap", $wrap, data.scrapList, data.scrapCount);
	// 페이징 처리
	showPage($pageWrap, data.page);
}

// ---------------------------------------------------------------

// 게시글 목록 표시
function renderList(temp, $wrap, list, count) {
	const $divWrap = $wrap.find(".div-post-list-wrap");
	
	if (count == 0) {
		const postNone = `
			<div class="div-post-none">
				아직 아무것도 남기지 않았어요.<br>당신의 방, 취향, 하루를 들려주세요.
			</div>`;
		$divWrap.html(postNone);
		return;
	}
	
	$divWrap.empty();
	const postWrap = list.map(post => `
		<div class="div-one-post-wrap post-hover ${post.checkCommunityImg ? 'has-img' : ''}" onclick="location.href='/community/community-view?communityId=${post.communityId}'">
		  <div class="${temp === "regist" ? 'div-my-post-info-wrap' : 'div-post-info-wrap'}">
		    <div class="${temp === "regist" ? 'div-post-text-wrap' : 'div-profile-post-wrap'}">
			${temp === "scrap"
			  ?	`<div class="div-profile-wrap">
			       <div class="div-go-user-page">
				     ${post.profileImgUuid === null
					   ? `<img src="/image/layout/profile_img_basic.png" alt="프로필">`
			           : `<img src="/upload/${post.profileImgPath}/th_${post.profileImgUuid}" alt="프로필">`}
			         <div class="div-nickname div-nick">${post.userNickname}</div>
			       </div>
			       <div class="div-age">${post.userAge}</div>
			     </div>`
			  : '' }
		      <div class="div-one-skip div-title">${post.communityTitle}</div>
		      <div class="div-two-skip div-content">${post.communityContent}</div>
		    </div>
			${post.checkCommunityImg
				? `<div class="div-none-profile">
				     <img src="/upload/${post.communityImgPath}/th_${post.communityImgUuid}" alt="postIMG">
				   </div>`
				: ''
			}
		  </div>
		  <div class="div-hashtag-wrap">
		  ${post.tags && post.tags.length > 0
			? post.tags.map(tag => `<div class="div-hashtag">#${tag}</div>`).join('')
			: ''
		  }
		  </div>
		  <div class="div-time-count-wrap">
		    <div class="div-time time-ago" data-timestamp="${post.createDate}"></div>
		    <div class="div-two-counts-wrap">
		      <div class="div-count-wrap">
		        <img src="/image/layout/comment_count.png" alt="comment">
		        <div class="div-comment">${post.commentCount}</div>
		      </div>
		      <div class="div-count-wrap">
			    ${temp === "regist"
					? `<img src="/image/layout/scrap_no.png" alt="scrap">`
					: `<img src="/image/layout/scrap_ok.png" onclick="noScrapMyPage(event, this, ${post.communityId})">`
				}
		        <div class="div-scrap">${post.scrapCount}</div>
		      </div>
		    </div>
		  </div>
		</div>`).join("");

	$divWrap.html(postWrap);
	// mypage/mypage.js - 화면에 보여지는 리스트의 높이로 계산하여 자동 설정
	temp === "regist" ? updateSlideHeight(".write-list-wrap") : updateSlideHeight(".scrap-list-wrap");
	
	// basic.js
	updateTimeAgo();
}

// ---------------------------------------------------------------

// 스크랩 취소 후 스크랩 목록 다시 불러오기
// basic.js (noScrap)
async function noScrapMyPage(event, element, toUserId) {
  await noScrap(event, element, toUserId);
  loadScrapData(scrapPage);
}

// ---------------------------------------------------------------

// 페이징 처리
function showPage($pageWrap, page) {
	let $prevWrap = $pageWrap.find(".previous").parent();
	let $nextWrap = $pageWrap.find(".next").parent();
	let $ul = $pageWrap.find("ul");
	
	// 이전 ◁
	if (page.prev) {
		$prevWrap.show();
		$prevWrap.find(".previous").data('page', (page.startPage - 1));
	} else {
		$prevWrap.hide();	
	}
	// 페이지 번호
	$ul.empty();
	for(let i = page.startPage; i <= page.endPage; i++) {
		const $li = $(`<li class="pagenation page" data-page="${i}">${i}</li>`);
		if (i === page.criteria.page) {
			$li.addClass("selected");
		}
		$ul.append($li);
	}
	// 다음 ▷
	if (page.next) {
		$nextWrap.show();
		$nextWrap.find(".next").data('page', (page.endPage + 1));
	} else {
		$nextWrap.hide();	
	}
}

// --------------------------------

// 페이지 이동 이벤트
$(document).off().on("click", ".pagenation", function() {
	const pageNum = $(this).data("page");
	
	if ($(this).closest(".div-mypage-container").hasClass("write-list-wrap")) {
		loadWriteData(pageNum);
		registPage = pageNum;
	} else if ($(this).closest(".div-mypage-container").hasClass("scrap-list-wrap")) {
		loadScrapData(pageNum);
		scrapPage = pageNum;
	}
});
