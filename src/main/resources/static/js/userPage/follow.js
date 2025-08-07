// 팔로워, 팔로우 모달창
// 모달 열기
function openFollowModal(temp, userList, modalId = "#MODAL-ALERT-ONE-A") {
    const $modal = $(modalId);
    const $alertWrap = $(".div-alert-wrap");
    const type = temp == "follower" ? "팔로워" : "팔로우";
    let userCount = userList.length;
    const title = `
        <div id="DIV-MODAL-HEADER">
            <div class="div-modal-title">${type}</div>
            <img src="/image/layout/close_btn_grey.png" class="modal-close modal-close-img" alt="close">
        </div>
    `;
    
    $modal.addClass("alert-active modal-follow");
    $alertWrap.empty();

    if (userCount === 0) {
        const userNone = temp == "follower" ? 
            `<div id="DIV-USER-NONE">
                아직 팔로워가 없어요.<br>내 활동이 쌓이면 누군가가 찾아올지도 몰라요 :)
            </div>`
            : `<div id="DIV-USER-NONE">
                아직 팔로우한 사람이 없어요.<br>관심 있는 사용자를 찾아 팔로우해보세요!
            </div>
            `;
        $alertWrap.html(title + userNone);
        return;
    }

    const followUserTopHtml = `
        <div class="div-alert-content">
            <div id="DIV-USER-SEARCH-WRAP">
                <input type="text" id="user-search" placeholder="누구를 찾고 있나요?">
            </div>
            <div id="DIV-USER-LIST-WRAP">
            </div>
        </div>
    `;
    $alertWrap.html(title + followUserTopHtml);

    userList.forEach(user => {
        const userWrap = `
            <div class="div-user-wrap">
                <div class="div-follow-user-wrap">
                <div class="div-user-profile-img user-profile-img">
				  ${ user.profileImgUuid !== null
                    ? `<img src="/upload/${user.profileImgPath}/th_${user.profileImgUuid}">`
                    : `<img src="/image/layout/profile_img_basic.png">`
				  }
                </div>
                <div class="div-user-simple-info">
                    <div class="user-nick"><span class="user-nickname">${user.userNickname}</span></div>
                    <div class="user-info-wrap">
                        <div class="user-info div-one-skip">${user.userIntro}</div>
                    </div>
                </div>
                </div>
                <div class="div-follow-btn-wrap">
                ${ temp === "follower"
                    ? (user.followed
                        ? `<div class="button-style basic-button" onclick="noFollow(event, this, ${user.userId})">팔로잉</div>`
                        : `<div class="button-style follow-btn" onclick="goFollow(event, this, ${user.userId})">팔로우</div>`
                    )
                    : `<div class="button-style delete-button" onclick="deleteFollow(event, this, 1)">삭제</div>`
                }
                </div>
            </div>
        `;

        $("#DIV-USER-LIST-WRAP").append(userWrap);
    });

    // 모달 표시
    $modal.fadeIn(200);
}

// ---------------------------------------------------------------

// errMsg는 basic.js

// 팔로워 목록
function showFollowerList(targetUserId) {
	console.log("targetUserId : " + targetUserId);
	fetch(`/follow/follower-list/${targetUserId}`, {
			method: "get"
	})
	.then(response => {
		console.log("응답 상태코드:", response.status);
		if (!response.ok) throw new Error("팔로워 목록 렌더링 실패");
		return response.json();
	})
	.then((data) => { openFollowModal("follower", data); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); })
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
