// 채팅방이 로드 되었을 때
$(document).ready(function(){
  // 처음 로드시 스크롤을 맨아래로
  $list.scrollTop($list[0].scrollHeight);
});

// --------------------------------------

const $list = $("#DIV-MESSAGE-LIST");
const $scrollBtn = $("#DIV-SCROLL-BTN-WRAP");

// 스크롤이 하단에서 일정 범위 올라가면 하단으로 스크롤 버튼 보이기/감추기
$list.on("scroll", function () {
  const nearBottom = $list[0].scrollTop + $list.outerHeight() >= $list[0].scrollHeight - 100;

  if (!nearBottom) {
    $scrollBtn.removeClass("none-show");
    $scrollBtn.fadeIn();
  } else {
    $scrollBtn.fadeOut();
    $scrollBtn.addClass("none-show");
  }
});

// 스크롤 버튼 클릭 시 맨 아래로 스크롤
$scrollBtn.on("click", scrollToBottom);

// --------------------------------------

// 맨 아래로 스크롤
function scrollToBottom() {
  $($list).animate({
    scrollTop: $($list)[0].scrollHeight
  }, 400);
}

// ---------------------------------------------------------------