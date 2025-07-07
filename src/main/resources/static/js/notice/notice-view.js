const deleteMsg = "이 글을 정말 지우시겠어요?<br>한 번 삭제하면, 다시 볼 수 없어요.";

// 게시글 삭제 버튼 클릭 시
$("#POST-DELETE-BTN").on("click", function() {
  openModal(deleteMsg, 2).then((result) => {
    if (result) {
      setTimeout(() => {
        openModal("삭제되었습니다.");
        // location.href = "삭제경로";
      }, 50);
    }
  });
});

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
  console.log($mainImg);
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