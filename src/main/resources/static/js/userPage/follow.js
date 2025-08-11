
// errMsg는 basic.js

// 팔로워 목록
function showFollowerList(targetUserId) {
	fetch(`/follow/follower-list/${targetUserId}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("팔로워 목록 렌더링 실패");
		return response.json();
	})
	.then((data) => { openFollowModal("follower", data); })
	.catch(() => { openModal(errMsg); })
}

// 팔로우 목록
function showFollowList(targetUserId) {
	fetch(`/follow/follow-list/${targetUserId}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("팔로우 목록 렌더링 실패");
		return response.json();
	})
	.then((data) => { openFollowModal("follow", data); })
	.catch(() => { openModal(errMsg); })
}

// ---------------------------------------------------------------

// 팔로워, 팔로우 모달창
// 모달 열기
function openFollowModal(temp, data, modalId = "#MODAL-ALERT-ONE-A") {
    const $modal = $(modalId);
    const $alertWrap = $(".div-alert-wrap");
    const type = temp == "follower" ? "팔로워" : "팔로우";
	
    $modal.addClass("alert-active modal-follow");
    const frame = `
		<div id="DIV-MODAL-HEADER">
		    <div class="div-modal-title">${type}</div>
		    <img src="/image/layout/close_btn_grey.png" class="modal-close modal-close-img" alt="close">
		</div>
		<div class="div-alert-content">
		    <div id="DIV-USER-SEARCH-WRAP">
		        <input type="text" id="user-search" class="${temp === "follower" ? 'input-follower' : 'input-follow'}" placeholder="누구를 찾고 있나요?">
		    </div>
		    <div id="DIV-USER-LIST-WRAP"></div>
		</div>
    `;
	
    $alertWrap.html(frame);

    // 모달 표시
	renderFollowList(temp, data);
    $modal.fadeIn(200);
}

// ----------------------------------------

function renderFollowList(temp, data) {
    const $listWrap = $("#DIV-USER-LIST-WRAP");
    $listWrap.empty();

	// 팔로우한 유저가 없을 경우
    if (data.count === 0) {
        const userNone = temp === "follower"
            ? `<div id="DIV-USER-NONE">아직 팔로워가 없어요.<br>내 활동이 쌓이면 누군가가 찾아올지도 몰라요 :)</div>`
            : `<div id="DIV-USER-NONE">아직 팔로우한 사람이 없어요.<br>관심 있는 사용자를 찾아 팔로우해보세요!</div>`;
        $(".div-alert-content").html(userNone);
        return;
    }
	
	// 팔로우한 유저는 있으나 검색한 결과가 없을 경우
    if (data.lists.length === 0) {
        const userNone = `<div id="DIV-USER-NONE">검색 결과가 없습니다.</div>`;
        $listWrap.html(userNone);
        return;
    }

    data.lists.forEach(user => {
        const userWrap = `
            <div class="div-user-wrap">
                <div class="div-follow-user-wrap div-go-user-page" data-user-id="${user.userId}">
                    <div class="div-user-profile-img user-profile-img">
                        ${user.profileImgUuid
                            ? `<img src="/upload/${user.profileImgPath}/th_${user.profileImgUuid}">`
                            : `<img src="/image/layout/profile_img_basic.png">`}
                    </div>
                    <div class="div-user-simple-info">
                        <div class="user-nick"><span class="div-nickname">${user.userNickname}</span></div>
                        <div class="user-info-wrap">
                            <div class="user-info div-one-skip">${user.userIntro}</div>
                        </div>
                    </div>
                </div>
                <div class="div-follow-btn-wrap">
                    ${temp === "follower"
                        ? (user.followed
                            ? `<div class="button-style basic-button" onclick="noFollow(event, this, ${user.userId})">팔로잉</div>`
                            : `<div class="button-style follow-btn" onclick="goFollow(event, this, ${user.userId})">팔로우</div>`)
                        : `<div class="button-style delete-button" onclick="deleteFollow(event, this, ${user.userId})">삭제</div>`}
                </div>
            </div>`;
        $listWrap.append(userWrap);
    });
}

// ----------------------------------------

// 팔로우 목록 검색 시
$(document).on("input change", ".input-follow", function() {
    const keyword = $(this).val();
    fetch(`/follow/follow-list/${userId}?keyword=${encodeURIComponent(keyword)}`)
        .then(res => res.json())
        .then(data => renderFollowList("follow", data))
        .catch(() => openModal(errMsg));
});

// 팔로워 목록 검색 시
$(document).on("input change", ".input-follower", function() {
    const keyword = $(this).val();
    fetch(`/follow/follower-list/${userId}?keyword=${encodeURIComponent(keyword)}`)
        .then(res => res.json())
        .then(data => renderFollowList("follower", data))
        .catch(() => openModal(errMsg));
});