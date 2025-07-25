// 게시글 등록 완료 후
const params = new URLSearchParams(window.location.search);
const writeOkMsg = "글이 정상적으로 업로드되었습니다.<br>많은 사람들이 보게 될 거예요!";
if (params.get("registOk") === "true") {
    openModal(writeOkMsg).then((result) => {
	if (result) {
		location.replace("/community/community-list");
	}
  });
}

// ---------------------------------------------------------------

// 정렬 카테고리 - 선택한 정렬에 따라 select 여백 조정
$("#SELECT-SORT").click(function() {
  let selectValue = $("#SELECT-SORT");
  
  if (selectValue.val() == "comment") {
    selectValue.css("padding", "0 15px");
  } else if (selectValue.val() == "scrap") {
    selectValue.css("padding", "0 32px");
  } else {
    selectValue.removeAttr("style");
  }
});

// ---------------------------------------------------------------

// 유저 페이지 이동
$(document).ready(function () {
  // 이미지 클릭 시
  $(".div-go-user-page img").on("click", goUserPage);

  // 닉네임 클릭 시
  $(".div-nickname").on("click", goUserPage);
});

function goUserPage(e) {
  e.stopPropagation();
  const userId = $(this).closest(".div-go-user-page").data("user-id");
  location.href = "/user/userPage?userId=" + userId;
}

// ---------------------------------------------------------------

// 게시글에 이미지가 존재할 경우 너비 조정
document.querySelectorAll(".div-post-info-wrap").forEach(wrap => {
  const imgWrap = wrap.querySelector(".div-post-img img");
  if (imgWrap && imgWrap.complete && imgWrap.naturalWidth > 0) {
    wrap.classList.add("has-img");
  }
});

// ---------------------------------------------------------------

// 글 작성 버튼 클릭 시
$(".div-write-btn").on("click", function() {
  const userNumber = $(this).data("userId");
  
  if (userNumber > 0) {
    location.href="community-regist";
  } else {
    openModal("로그인이 필요해요.<br>글 작성은 로그인 후 이용할 수 있어요!");
  };
});

// ---------------------------------------------------------------

