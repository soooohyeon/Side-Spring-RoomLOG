
const STATE_KEY = "mypage_state"; 	// 세션 저장 키
const WRAP_FRAME = { 'my-board': '.write-list-wrap', 'my-scrap': '.scrap-list-wrap' };
let checkRestoring = false;			// 복원 중인지 여부 체크
let restoreScrollY = null;			// 복원할 스크롤 위치

// --------------------------------

// 선택한 카테고리 읽기
function getActiveCategory() {
  return $('.div-category.div-selected').data('category') || 'my-board';
}

// --------------------------------

// 기존 카테고리 설정 불러와서 활성화 표시
function setActiveCategory(category) {
  // 카테고리 탭 버튼 상태
  $('.div-category').removeClass('div-selected');
  $(`.div-category[data-category="${category}"]`).addClass('div-selected');

  // 활성화된 카테고리 목록 보여주기
  $('.div-mypage-container').hide();
  $(WRAP_FRAME[category]).show();

  // myPage.js - 높이 자동조절 함수 사용
  requestAnimationFrame(() => updateSlideHeight(WRAP_FRAME[category]));
}

// --------------------------------

// 페이지 이동 전 현재 상태(현재 활성화된 카테고리, 페이지, 스크롤 위치) 저장
function saveMyPageState() {
  sessionStorage.setItem(STATE_KEY, JSON.stringify({
    activeCategory: getActiveCategory(),
    registPage,
    scrapPage,
    scrollY: window.scrollY
  }));
}

// --------------------------------

// 상태 복원
$(function restoreMyPageState() {
	const raw = sessionStorage.getItem("mypage_state");
	if (!raw) return;
	const active = JSON.parse(raw);
	const activeCategory  = (active.activeCategory === 'my-scrap') ? 'my-scrap' : 'my-board';
	const page = (activeCategory === 'my-scrap') ? (active.scrapPage || 1) : (active.registPage || 1);
		
	// myPage.js - 해당 li-category-wrap에 걸려있는 click 이벤트 그대로 실행
	$(`.div-category[data-category="${activeCategory}"]`).closest(".li-category-wrap").trigger("click");
		
	// 원하는 목록 로드
	activeCategory === 'my-scrap' ? loadScrapData(page) : loadWriteData(page);

	// 스크롤 위치 복원
	requestAnimationFrame(() => window.scrollTo(0, active.scrollY || 0));
	// 저장한 상태 삭제
	sessionStorage.removeItem("mypage_state");
});

// ---------------------------------------------------------------

// errMsg는 basic.js
// 페이지 기본은 1
let registPage = 1;
let scrapPage = 1;

// --------------------------------

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
			
	// 렌더 끝난 후 한 프레임 뒤 스크롤 복원
	if (restoreScrollY !== null) {
		requestAnimationFrame(() => {
			window.scrollTo(0, restoreScrollY);
			restoreScrollY = null;
		});
	}
}

// --------------------------------

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
		
	// 렌더 끝난 후 한 프레임 뒤 스크롤 복원
	if (restoreScrollY !== null) {
		requestAnimationFrame(() => {
			window.scrollTo(0, restoreScrollY);
			restoreScrollY = null;
		});
	}
}

// --------------------------------

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
		$wrap.find("#DIV-PAGENATION-WRAP").remove();
		return;
	}
	
	$divWrap.empty();
	const postWrap = list.map(post => `
		<div class="div-one-post-wrap post-hover ${post.checkCommunityImg ? 'has-img' : ''}" onclick="goCommunityView(${post.communityId})">
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

// 커뮤니티 상세페이지 이동
function goCommunityView(communityId) {
	saveMyPageState();
	location.href = "/community/community-view?communityId=" + communityId;
}

// ---------------------------------------------------------------

// 스크랩 취소 후 스크랩 목록 다시 불러오기
// basic.js (noScrap)
function noScrapMyPage(event, element, toUserId) {
  noScrap(event, element, toUserId);
  setTimeout(() => {
    loadScrapData(scrapPage); // 기존 함수 그대로 호출
  }, 180);
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
