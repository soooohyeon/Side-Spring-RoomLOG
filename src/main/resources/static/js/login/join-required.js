$(document).ready(function () {

  let isCheckNick = false;
  let isCheckBirth = false;
  let isCheckAgeVisible = true;

  // 닉네임 중복 검사
  $("#nickname").on("input", function() {
    let $nickResult = $("#DIV-NICK-CHECK")
    let nickValue = $(this).val();
    
    // basic.js
    isCheckNick = isNickUsed($nickResult, nickValue);
    updateJoinButton();
  });

  // 사용 기간 기본 값과 최대 날짜를 현재 날짜로 설정
  const today = new Date().toISOString().substring(0,10);
  $("#birth").attr("max", today);

  // 생년월일 유효성 검사
  $("#birth").on("input", function() {
    let birthValue =$(this).val();
    isCheckBirth = birthValue == "" ? false : true;
    updateJoinButton();
  });

  // 나이 공개 여부 유효성 검사
  $(`input[type="radio"][name="isAgeVisible"]`).on("change", function() {
    let ageVisibleValue = $(this).val();
    isCheckAgeVisible = ageVisibleValue == "" ? false : true;
    updateJoinButton();
  });

  // 회원가입 유효성 검사
  function updateJoinButton() {
    const $btn = $(".btn-no-join, .btn-go-join");
    if (isCheckNick && isCheckBirth && isCheckAgeVisible) {
      $btn
        .removeAttr("disabled")
        // .attr("type", "submit")
        .addClass("btn-go-join")
        .removeClass("btn-no-join");
    } else {
      $btn
        .attr("disabled", true)
        .removeClass("btn-go-join")
        .addClass("btn-no-join");
    } 
  }
});

// ---------------------------------------------------------------

// 회원 가입 버튼 클릭 시
const joinMsg = "그대로 진행하시겠어요?";
$(document).on("click", ".btn-go-join", function() {
  openModal(joinMsg, 2).then((result) => {
	if (result) {
		$("#joinForm").submit();
	}
  });
});