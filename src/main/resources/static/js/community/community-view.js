// ---------------------------------------------------------------
// 댓글을 제외한 상세페이지 이벤트 관련

const deleteMsg = "이 글을 정말 지우시겠어요?<br>한 번 삭제하면, 다시 볼 수 없어요.";

// 게시글 삭제 버튼 클릭 시
$("#POST-DELETE-BTN").on("click", function() {
  openModal(deleteMsg, 2).then((result) => {
    if (result) {
      location.href = "/community/community-delete";
    }
  });
});

// ---------------------------------------------------------------

// 이미지 관련
// 메인 이미지 - 높이 자동 조절
function setBoxHeight() {
  const $box = $("#DIV-MAIN-IMAGE");
  const width = $box.outerWidth();
  $box.css("height", width * 0.7 + "px");
}
// 메인 이미지 - 로딩 + 리사이즈 이벤트에 적용
$(window).on("load resize", setBoxHeight);

// 이미지 리스트 - 높이 자동 조절
function setMiniBoxHeight() {
  const $box = $(".div-sub-image");
  const width = $box.outerWidth();
  $box.css("height", width * 0.8 + "px");
}
// 이미지 리스트 - 로딩 + 리사이즈 이벤트에 적용
$(window).on("load resize", setMiniBoxHeight);

$(document).ready(function() {
  const $mainImg = $(".div-sub-image > img").attr("src");
  $("#DIV-MAIN-IMAGE > img").attr("src", $mainImg);
});

// 메인 이미지 띄우기
function changeImage(element) {
  const newSrc = $(element).attr("src");
  const $mainImage = $("#DIV-MAIN-IMAGE img");

  $mainImage.fadeOut(100, function () {
    $mainImage.attr("src", newSrc).fadeIn(150);
  });
}

// ---------------------------------------------------------------

// 유저 페이지 이동
$(document).ready(function () {
  // 이미지 클릭 시
  $(".div-go-user-page img").on("click", goUserPage);

  // 닉네임 클릭 시
  $(".div-nick").on("click", goUserPage);
});

// ---------------------------------------------------------------

// 스크랩
// 스크랩이 아닌 상태에서 버튼 호버시
$('.blue-line-button').hover(function () {
  const $img = $(this).children("img");
  const src = $img.attr("src");

  // 현재 이미지가 스크랩된 상태일 때만 hover 이미지로 바꾸기
  if (src.includes("scrap_ok.png")) {
    $img.attr("src", "/image/community/scrap_full_hover.png");
  }
}, function () {
  const $img = $(this).children("img");
  const src = $img.attr("src");

  // hover에서 벗어날 때 다시 원래 이미지로 복귀
  if (src.includes("scrap_full_hover.png")) {
    $img.attr("src", "/image/layout/scrap_ok.png");
  }
});

// 스크랩 하기
function goPostScrap(element, communityId) {
  if (userId > 0 && userId != null) {
    fetch(`/scrap/scrap-save/${communityId}`, {
  	  method: 'POST'
    })
    .then(response => {
      if (!response.ok) throw new Error("스크랩 실패");
  	  element.setAttribute("class", "button-style basic-button");
  	  element.setAttribute("onclick", `noPostScrap(this, ${communityId})`);
  	  element.children[0].src = "/image/community/scrap_white.png";
      // 현재 스크랩 수 + 1
      const $count = $("#DIV-SCRAP");
      const current = parseInt($count.text());
      $count.text(current + 1);
    })
    .catch(() => {
      openModal(errMsg);
    });
  }	else if (userId == null) {
	openModal("로그인이 필요해요.<br>스크랩은 로그인 후 이용할 수 있어요!");
  }
}

// 스크랩 해제
function noPostScrap(element, communityId) {
  fetch(`/scrap/scrap-cancel/${communityId}`, {
  	method: 'DELETE'
  })
  .then(response => {
    if (!response.ok) throw new Error("스크랩 취소 실패");
	element.setAttribute("class", "button-style blue-line-button");
	element.setAttribute("onclick", `goPostScrap(this, ${communityId})`);
	element.children[0].src = "/image/layout/scrap_ok.png";
    // 현재 스크랩 수 - 1
    const $count = $("#DIV-SCRAP");
    const current = parseInt($count.text());
    $count.text(current - 1);
  })
  .catch(() => {
    openModal(errMsg);
  });
}
