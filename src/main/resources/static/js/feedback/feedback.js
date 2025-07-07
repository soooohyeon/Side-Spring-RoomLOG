$(document).ready(function() {
  const $content = $("#TEXTAREA-CONTENT");

  // 실시간 글자 수 표시
  $content.on("change input", countContent);
});

// 글자 수 카운트 (공백, 줄바꿈 포함)
function countContent() {
  const $contentCount = $("#SPAN-COUNT");

  // 이모지 포함 정확한 길이 계산
  let content = $(this).val();
  let trimmed = Array.from(content).slice(0, 500).join("");

  // 200자까지만 입력
  if (content !== trimmed) {
    $(this).val(trimmed);
    content = trimmed;
  }

  const nowLength = Array.from(content).length;
  // 글자수 표시
  $contentCount.text(nowLength);
}

// --------------------------------------------------------------- 

// 카테고리, 제목, 본문 유효성 검사
// 회원가입 유효성 검사
let isCheckCategory = false;
let isCheckTitle = false;
let isCheckContent = false;

// 제목 값 확인
$(`select[name="category"]`).on("change", function() {
  isCheckCategory = $(this).val() == "" ? false : true;
  console.log($(this).val());
  writeButton();
  updateBackBlock();
});

// 제목 값 확인
$("input[name='title']").on("input keyup", function() {
  isCheckTitle = $(this).val() == "" ? false : true;
  writeButton();
  updateBackBlock();
});

// 내용 값 확인
$("textarea").on("input keyup", function() {
  isCheckContent = $(this).val() == "" ? false : true;
  writeButton();
  updateBackBlock();
});

function writeButton() {
  const $btn = $("#WRITE-BTN");
  if (isCheckCategory && isCheckTitle && isCheckContent) {
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

const writeMsg = "소중한 의견을 전달하시겠어요?<br>여러분의 건의는 더 나은 서비스를 만드는 데 큰 도움이 됩니다.";
const writeOkMsg = "건의사항이 정상적으로 제출되었습니다.<br>처리 결과는 별도로 안내되지 않으니 참고해 주세요.";

// 글 등록 버튼 클릭 시
$(document).on("click", ".basic-button", function() {
  openModal(writeMsg, 2).then((result) => {
    if (result) {
      setTimeout(() => {
        openModal(writeOkMsg);
        // location.href="";
      }, 50);
    }
  });
});

// --------------------------------------------------------------- 

// 값이 입력된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우기
let isBlocking = false;   // 뒤로가기 막을지에 대한 여부
let hasPushed = false;    // 뒤로가기 눌렀는지에 대한 여부
let isModalOpen = false;  // 중복 모달 방지를 위한 flag 변수
const moveMsg = "작성 중인 내용이 저장되지 않습니다.<br>정말 이동하시겠습니까?";

// 입력된 값이 있는지 확인
function isFormFilled() {
  const file = $("input[name='images']")[0]?.files.length > 0;
  
  return isCheckCategory || isCheckTitle || isCheckContent || file;
}

// 뒤로가기 눌렀을 때
function updateBackBlock() {
  const filled = isFormFilled();
  // 값이 채워졌을 때 
  if (filled && !hasPushed) {
    history.pushState({ preventBack: true }, "", location.href);
    hasPushed = true;
    isBlocking = true;
  }

  // 값이 비워졌을 때
  if (!filled && hasPushed) {
    history.back();
    hasPushed = false;
    isBlocking = false;
  }
}

// 값을 입력 후 다시 지웠을 때 히스토리 복구하기 위함
$(document).ready(function () {
  $("input[name='images']").on("change", updateBackBlock);

  // 약간의 지연을 두고 TinyMCE 초기화 끝나길 기다렸다가 실행
  setTimeout(updateBackBlock, 100); // ✅ 초기에 한번 강제 실행
});

// 페이지 이동 시
$(document).on("click", "a, button, [data-navigate]", function (e) {
  const href = $(this).attr("href") || $(this).data("href");
  if (href && isFormFilled()) {
    e.preventDefault(); // 기본 이동 막음
    openModal(moveMsg, 2).then((result) => {
      if (result) {
        isBlocking = false;
        hasPushed = false;
        location.href = href;
      }
    });
  }
});

// 뒤로가기 눌렀을 때
window.addEventListener("popstate", function (e) {
  if (isBlocking && isFormFilled() && !isModalOpen) {
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
