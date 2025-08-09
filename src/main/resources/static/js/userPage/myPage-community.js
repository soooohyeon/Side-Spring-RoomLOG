// errMsg는 basic.js

loadWriteData();

// 내가 작성한 게시글
function loadWriteData() {
	fetch(`/mypage/community/regist-list`, {
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
	const $registWrap = $wrap.find(".div-post-list-wrap");
	
	$countDiv.html(data.registCount);
	renderWriteList($registWrap, data.registList, data.registCount);
}

// 실제 작성한 게시글 목록 표시
function renderWriteList($wrap, list, count) {
	if (count == 0) {
		const postNone = `
			<div class="div-post-none">
				아직 아무것도 남기지 않았어요.<br>당신의 방, 취향, 하루를 들려주세요.
			</div>`;
		$wrap.html(postNone);
		return;
	}
	
	console.log(list.length);
	$wrap.empty();
	list.forEach(post => {
		const postWrap = `
			<div class="div-one-post-wrap post-hover ${post.checkCommunityImg ? 'has-img' : ''}" onclick="location.href='/community/community-view'">
			  <div class="div-my-post-info-wrap">
			    <div class="div-post-text-wrap">
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
			        <img src="/image/layout/scrap_no.png" alt="scrap">
			        <div class="div-scrap">${post.scrapCount}</div>
			      </div>
			    </div>
			  </div>
			</div>
		`;

		$wrap.append(postWrap);

		// basic.js
		updateTimeAgo();
	});
	
}

/*// 내가 스크랩한 게시글
function loadWriteData() {
	fetch(`/mypage/community/scrap-list`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("내가 스크랩한 게시글 렌더링 실패");
		return response.json();
	})
	.then((data) => { renderWriteList(data); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); });
}*/