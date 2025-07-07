
// 게시글에 이미지가 존재할 경우 너비 조정
document.querySelectorAll(".div-one-post-wrap").forEach(wrap => {
    const noneProfileImgWrap = wrap.querySelector(".div-none-profile img");
    const imgWrap = wrap.querySelector(".div-post-img img");
    if (noneProfileImgWrap && noneProfileImgWrap.complete && noneProfileImgWrap.naturalWidth > 0) {
        wrap.classList.add("has-img");
    }
    if (imgWrap && imgWrap.complete && imgWrap.naturalWidth > 0) {
        wrap.classList.add("has-img");
    }
});

// ---------------------------------------------------------------

// 팔로워, 팔로우 모달창
// 모달 열기
function openFollowModal(temp, modalId = "#MODAL-ALERT-ONE-A") {
    const $modal = $(modalId);
    const $alertWrap = $(".div-alert-wrap");
    const type = temp == 1 ? "팔로워" : "팔로우";
    let userCount = userList.length;
    const title = `
        <div id="DIV-MODAL-HEADER">
            <div class="div-modal-title">${type}</div>
            <img src="../../image/layout/close_btn_grey.png" class="modal-close modal-close-img" alt="close">
        </div>
    `;
    
    $modal.addClass("alert-active modal-follow");
    $alertWrap.empty();

    if (userCount === 0) {
        const userNone = temp == 1 ? 
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
                    <img src="${user.profileImgSrc}">
                </div>
                <div class="div-user-simple-info">
                    <div class="user-nick"><span class="user-nickname">${user.nick}</span></div>
                    <div class="user-info-wrap">
                        <div class="user-info div-one-skip">${user.info}</div>
                    </div>
                </div>
                </div>
                <div class="div-follow-btn-wrap">
                ${ temp === 1
                    ? (user.isFollow === 0
                        ? `<div class="button-style follow-btn" onclick="goFollow(event, this, 0)">팔로우</div>`
                        : `<div class="button-style basic-button" onclick="noFollow(event, this, 1)">팔로잉</div>`
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

// ----------------------------------------

// 팔로워 클릭 시
$(".show-follower").on("click", function() {
    openFollowModal(1);
});
// 팔로우 클릭 시
$(".show-follow").on("click", function() {
    openFollowModal(2);
});

// ---------------------------------------------------------------

// 유저 검색
$(document).on("keyup", "#user-search", function() {
    console.log($(this).val());
});

// ---------------------------------------------------------------

// 임시 유저 배열
const userList = [
    { 
        id : 1,
        nick : "고양이좋아",
        info : "집에 햇살과 고양이가 함께 있어요.",
        profileImgSrc : "../../image/example/profile_image/profile1.jpg",
        isFollow : 1
    },
    { 
        id : 2,
        nick : "작은식탁일기",
        info : "",
        profileImgSrc : "../../image/example/profile_image/profile2.jpg",
        isFollow : 1
    },
    { 
        id : 3,
        nick : "창가쪽자리",
        info : "햇빛이 드는 창 옆에서 조용히 기록해요.",
        profileImgSrc : "../../image/example/profile_image/profile3.jpg",
        isFollow : 0
    },
    { 
        id : 4,
        nick : "반쯤열린창",
        info : "바람이 머물다 가는 자리를 좋아해요.",
        profileImgSrc : "../../image/example/profile_image/profile4.jpg",
        isFollow : 1
    },
    { 
        id : 5,
        nick : "가만히있기",
        info : "오래 머무를 수 있는 공간 찾는 중",
        profileImgSrc : "../../image/example/profile_image/profile5.jpg",
        isFollow : 0
    },
    { 
        id : 6,
        nick : "잠시멈춤",
        info : "빠르게 흐르는 시간 속에서, 가끔은 숨 고르듯 멈춰 서요.",
        profileImgSrc : "../../image/example/profile_image/profile6.jpg",
        isFollow : 1
    },
    { 
        id : 7,
        nick : "조용히기록",
        info : "소란스럽지 않게, 조용한 마음으로 하루를 적어 내려갑니다.",
        profileImgSrc : "../../image/example/profile_image/profile7.jpg",
        isFollow : 1
    },
    { 
        id : 8,
        nick : "고먐미탐지기",
        info : "ㅋㅋ",
        profileImgSrc : "../../image/example/profile_image/profile8.jpg",
        isFollow : 1
    },
    { 
        id : 9,
        nick : "간식어디감",
        info : "아무 일도 없는 평범한 오후에 꾸는 낮잠이 제일 좋더라고요.",
        profileImgSrc : "../../image/example/profile_image/profile9.jpg",
        isFollow : 0
    },
    { 
        id : 10,
        nick : "빈노트한권",
        info : "아무것도 채워지지 않은 페이지에서 오늘을 시작합니다.",
        profileImgSrc : "../../image/example/profile_image/profile10.jpg",
        isFollow : 0
    }
];

// const userList = [];
