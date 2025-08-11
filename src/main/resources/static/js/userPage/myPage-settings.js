$(document).ready(function () {

  let isCheckNick = true;
  let isCheckAgeVisible = true;

  // 닉네임 중복 검사
  $("#INPUT-NICK").on("input", function() {
    let $nickResult = $("#DIV-NICK-CHECK");
    let nickValue = $(this).val();
    const originalNick = $(this).data("original");

    // basic.js
    isCheckNick = isNickUsed($nickResult, nickValue, originalNick);
    updateJoinButton();
  });

  // --------------------------------------

  // 한 줄 소개 30자 제한
  $("#INPUT-INTRO").on("input", function() {
    let $introValue = $(this);
    // basic.js
    limitUserIntroLength ($introValue);
  });
  
  // --------------------------------------

  // 나이 공개 여부 유효성 검사
  $(`input[type="radio"][name="isAgeVisible"]`).on("change", function() {
    let ageVisibleValue = $(this).val();
    isCheckAgeVisible = ageVisibleValue == "" ? false : true;
    updateJoinButton();
  });

  // --------------------------------------

  // 프로필 이미지 미리보기
  $("#profile-image").on("change", function(event){
    const file = event.target.files[0];
    // basic.js
    setOnePreview(file);
  });

  // --------------------------------------

  // 저장하기 버튼 유효성 검사
  function updateJoinButton() {
    const $btn = $(".disabled, .basic-button");
    if (isCheckNick && isCheckAgeVisible) {
      $btn
        .addClass("basic-button")
        .removeClass("disabled");
    } else {
      $btn
        .removeClass("basic-button")
        .addClass("disabled");
    } 
  }
});

// ---------------------------------------------------------------

// 프로필 이미지 등록 버튼 호버
const $imgBtn = $("#IMG-PROFILE-IMAGE-BTN");
$('#LABEL-PROFILE-IMAGE-BTN').hover(function() {
  $imgBtn.attr("src", "/image/userPage/profile_img_update_btn_hover.png");
}, function() {
  $imgBtn.attr("src", "/image/userPage/profile_img_update_btn.png");
});

// ---------------------------------------------------------------

// 저장하기 버튼 클릭 시
$(document).on("click", ".basic-button", function() {
  openModal("변경하신 내용이 저장되었습니다.")
});

// ---------------------------------------------------------------

// 값이 변경된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우기
let isBlocking = false;   // 뒤로가기 막을지에 대한 여부
let hasPushed = false;    // 뒤로가기 눌렀는지에 대한 여부
let isModalOpen = false;  // 중복 모달 방지를 위한 flag 변수
const moveMsg = "저장하지 않으면 변경한 내용이 사라집니다.<br>정말 이동하시겠습니까?";

// 수정된 값이 있는지 확인
function isFormEdited() {
  const $nickname = $("input[name='nickname']");
  const $intro = $("input[name='intro']");
  const $ageVisible = $("input[name='isAgeVisible']:checked");
  const $originalNickname = $nickname.data("original");
  const $originalIntro = $intro.data("original");
  const $originalAgeVisible = $ageVisible.data("original");


  console.log("변경 :  ", $ageVisible.val());
  console.log("기존  : ", $originalAgeVisible);
  console.log($ageVisible.val() !== $originalAgeVisible);
  console.log($nickname.val() !== $originalNickname);
  return $nickname.val() !== $originalNickname || $intro.val() !== $originalIntro || $ageVisible.val() !== $originalAgeVisible;
}

// 뒤로가기 눌렀을 때
function updateBackBlock() {
  const editStatus = isFormEdited();

  // 값이 변경됐을 때 
  if (editStatus && !hasPushed) {
    history.pushState({ preventBack: true }, "", location.href);
    hasPushed = true;
    isBlocking = true;
  }

  // 값이 똑같아졌을 때
  if (!editStatus && hasPushed) {
    history.back();
    hasPushed = false;
    isBlocking = false;
  }
}

// 값을 입력 후 다시 지웠을 때 히스토리 복구하기 위함
$(document).ready(function () {
  $("input").on("input", updateBackBlock);
  $("input[name='age-visible']").on("change", updateBackBlock);
});

// 페이지 이동 시
$(document).on("click", "a, button, [data-navigate]", function (e) {
  const href = $(this).attr("href") || $(this).data("href");
  if (href && isFormEdited()) {
    e.preventDefault(); // 기본 이동 막음
    openModal(moveMsg, 2).then((result) => {
      if (result) {
        isBlocking = false;
        hasPushed = false;
        location.href = href;
      }
      isModalOpen = false;
    });
  }
});

// 뒤로가기 눌렀을 때
window.addEventListener("popstate", function (e) {
  if (isBlocking && isFormEdited() && !isModalOpen) {
    isModalOpen = true;
    openModal(moveMsg, 2).then((result) => {
      if (result) {
        isBlocking = false;
        hasPushed = false;
        history.back();
      } else {
        history.pushState({ preventBack: true }, "", location.href);
      }
      isModalOpen = false;
    });
  }
});
