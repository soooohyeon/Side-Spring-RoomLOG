$(document).ready(function() {
  // 새 댓글
  const $message = $("#TEXTAREA-MESSAGE");
  
  // 로그아웃 상태면 textarea 비활성화
  $message.on("focus", function (e) {
    // openModal("로그인 후 이용해 주세요.");
    // $textarea.blur(); // 포커스 다시 제거
  });
  // 실시간 글자 수 표시, 등록 버튼 활성화
  $message.on("change input", function() {
    countMessage(this);
    updateBackBlock();
  });
});

// --------------------------------------------

// 새 댓글 작성 시 - 실시간 글자 수 표시, 등록 버튼 활성화
function countMessage(target) {
  const $messageCount = $("#SPAN-COUNT");

  // 이모지 포함 정확한 길이 계산
  let content = $(target).val();
  let trimmed = Array.from(content).slice(0, 300).join("");

  // 200자까지만 입력
  if (content !== trimmed) {
    $(this).val(trimmed);
    content = trimmed;
  }

  const commentLength = Array.from(content).length;
  const sendBtn = $("#WRITE-BTN");

  // 글자수 표시
  $messageCount.text(commentLength);
  
  // 1자 이상 입력 시 클래스 부여
  if (commentLength > 0) {
    sendBtn.attr("class", "button-style basic-button");
    isMessageCheck = true;
  } else {
    sendBtn.attr("class", "button-style disabled");
    isMessageCheck = false;
  }
  console.log("isMessageCheck  :  ", isMessageCheck);
}

// --------------------------------------------

// 메세지 보내기 버튼 클릭 시
$(document).on("click", ".basic-button", function() {
  const user = "반쯤열린창";
  const sendMsg = `메시지가 전송되었습니다.<br>${user} 님의 답장을 기다려보세요.`;
  openModal(sendMsg);
});

// --------------------------------------------------------------- 

// 값이 입력된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우기
let isBlocking = false;   // 뒤로가기 막을지에 대한 여부
let hasPushed = false;    // 뒤로가기 눌렀는지에 대한 여부
let isModalOpen = false;  // 중복 모달 방지를 위한 flag 변수
let isMessageCheck = false;
const moveMsg = "아직 전송하지 않은 메시지가 있어요.<br>다른 곳으로 이동하면 입력한 내용은 모두 사라집니다.<br>그래도 이동하시겠어요?";

// 뒤로가기 눌렀을 때
function updateBackBlock() {
  // 값이 채워졌을 때 
  if (isMessageCheck && !hasPushed) {
    history.pushState({ preventBack: true }, "", location.href);
    hasPushed = true;
    isBlocking = true;
  }

  // 값이 비워졌을 때
  if (!isMessageCheck && hasPushed) {
    history.back();
    hasPushed = false;
    isBlocking = false;
  }
}

// 페이지 이동 시
$(document).on("click", "a, button, [data-navigate]", function (e) {
  const href = $(this).attr("href") || $(this).data("href");
  if (href && isMessageCheck) {
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
  if (isBlocking && isMessageCheck && !isModalOpen) {
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
