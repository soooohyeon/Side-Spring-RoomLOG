
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

// /userPage/follow.js
// 팔로워 클릭 시
$(".show-follower").on("click", function() {
    openFollowModal("follower");
});
// 팔로우 클릭 시
$(".show-follow").on("click", function() {
    openFollowModal("follow");
});

// ---------------------------------------------------------------

// 유저 검색
$(document).on("keyup", "#user-search", function() {
    console.log($(this).val());
});
