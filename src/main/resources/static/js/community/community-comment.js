// ---------------------------------------------------------------
// 댓글

// 답글 버튼 호버시
$(document).on("mouseenter", ".div-re-comment-btn", function() {
  const $img = $(this).children("img");
  const src = $img.attr("src");
  
  // hover 이미지로 바꾸기
  if (src.includes("re_comment_btn.png")) {
    $img.attr("src", "/image/community/re_comment_btn_hover.png");
  }
});

$(document).on("mouseleave", ".div-re-comment-btn", function() {
  const $img = $(this).children("img");
  const src = $img.attr("src");
  
  // hover에서 벗어날 때 다시 원래 이미지로 복구
  if (src.includes("re_comment_btn_hover.png")) {
    $img.attr("src", "/image/community/re_comment_btn.png");
  }
});

// ---------------------------------------------------------------

// 댓글 입력시 줄 수에 따라 입력칸 높이 자동 조절
function setResizeTextArea(textarea) {
  if (!textarea) return;

  textarea.style.height = 'auto';
  textarea.style.height = textarea.scrollHeight + 'px';
}

// 값 입력시 자동 조절 함수 호출
['keyup', 'input'].forEach(evt => {
  document.addEventListener(evt, (e) => {
    if (e.target.tagName === 'TEXTAREA') {
      setResizeTextArea(e.target);
    }
  });
});

// ---------------------------------------------------------------

// 유저 페이지 이동 - basic.js
// 이미지 클릭 시
$(document).on("click", ".div-go-user-page img", goUserPage);

// 닉네임 클릭 시
$(document).on("click", ".div-nick", goUserPage);

// ---------------------------------------------------------------
