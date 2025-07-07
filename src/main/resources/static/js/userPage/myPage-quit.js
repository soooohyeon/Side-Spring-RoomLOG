let isQuitMsgCheck = false;

// 탈퇴 문구 넣으면 버튼 활성화
$("#INPUT-QUIT-MSG").on("input", function() {
  const inputValue = $(this).val();

  isQuitMsgCheck = inputValue === "탈퇴합니다";

  updateQuitButton();
});

// ---------------------------------------------------------------

// 탈퇴 버튼 활성화
function updateQuitButton() {
  const $btn = $(".disabled, .basic-button");
  if (isQuitMsgCheck) {
    $btn
      .addClass("basic-button")
      .removeClass("disabled");
  } else {
    $btn
      .removeClass("basic-button")
      .addClass("disabled");
  } 
}

// ---------------------------------------------------------------

const quitMsg = "탈퇴가 정상적으로 처리되었습니다.<br>소중한 시간을 함께해 주셔서 고맙습니다.";

// 탈퇴하기 버튼 클릭 시
$(document).on("click", ".basic-button", function() {
  openModal(quitMsg)
});